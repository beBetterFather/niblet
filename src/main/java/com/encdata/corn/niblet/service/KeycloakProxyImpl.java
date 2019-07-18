package com.encdata.corn.niblet.service;

import com.encdata.corn.niblet.dto.keycloak.*;
import com.encdata.corn.niblet.dto.keycloak.roles.KeycloakClientRoleElementDto;
import com.encdata.corn.niblet.dto.keycloak.roles.KeycloakRolesDto;
import com.encdata.corn.niblet.dto.keycloak.roles.PublicRolesDto;
import com.encdata.corn.niblet.dto.keycloak.roles.UserRolesDto;
import com.encdata.corn.niblet.service.intf.KeycloakProxy;
import com.encdata.corn.niblet.utils.JsonUtil;
import com.encdata.corn.niblet.utils.KeycloakUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 多线程获取
 * @Author Siwei Jin
 * @Date 2018/10/16 16:25
 */
@Component
public class KeycloakProxyImpl implements KeycloakProxy {
    private static final Logger LOG = LoggerFactory.getLogger(KeycloakProxyImpl.class);

    @Value(value = "${keycloak.realm}")
    private String realm;

    @Value(value = "${keycloak.auth-server-url}")
    private String authUrl;

    @Value(value = "${keycloak.resource}")
    private String clientId;

    @Value(value = "${keycloak.credentials.secret}")
    private String secret;

    @Override
    public String getAccessToken() {

        final String url=getAuthBaseUrl()+"/realms/"+getRealm()+"/protocol/openid-connect/token";
        //报头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        //报文内容
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", getClientID());
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_secret", getSecretId());

        //报文
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, headers);

        //post
        RestTemplate template = new RestTemplate();
        ResponseEntity<AccessTokenExt> responseEntity = template.postForEntity(url, requestEntity, AccessTokenExt.class);
        return responseEntity.getBody().getAccess_token();
    }

    @Override
    public List<KeycloakGroupExt> getUserGroupInfo(String userId) {
        List<KeycloakGroupExt> list = Lists.newArrayList();

        String url = getAuthBaseUrl()+"/admin/realms/"+getRealm()+"/users/"+userId+"/groups";

        String token = getAccessToken();
        //报头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "bearer "+token);

        //报文
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        RestTemplate template = new RestTemplate();
        //post
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        if(null!= responseEntity && null!= responseEntity.getBody()){
            KeycloakGroupExt[] groupArray = JsonUtil.readValue(responseEntity.getBody(), KeycloakGroupExt[].class);
            list =  Arrays.asList(groupArray);
        }
        return list;
    }

    @Override
    public List<GroupElementDto> getGroups() {
        List<GroupElementDto> rets = Lists.newArrayList();
        //1、设置访问keycloak的地址
        String url = getAuthBaseUrl()+"/admin/realms/"+getRealm()+"/groups";

        //2、获取访问keycloak所需token
        String token = getAccessToken();

        //3、设置访问报头，将2部获取到的token封装进报头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "bearer "+token);

        //4、设置报文
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

        //5、调用keyloak接口
        ResponseEntity<String> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);
        if(null!= responseEntity && null!= responseEntity.getBody()){
            GroupDto[] groupArray = JsonUtil.readValue(responseEntity.getBody(), GroupDto[].class);
            List<GroupDto> list = Arrays.asList(groupArray);
            //6、封装成pap返回的数据格式
            Map<String, String> maps = Maps.newHashMap();
            contertToPAP(list, maps, rets);
        }
        return rets;
    }

    @Override
    public List<GroupMembersDto> getGroupmembers(String groupId) {
        List<GroupMembersDto> rets = Lists.newArrayList();
        //1、设置访问keycloak的地址
        String url = getAuthBaseUrl()+"/admin/realms/"+getRealm()+"/groups/"+groupId+"/members";

        //2、获取访问keycloak所需token
        String token = getAccessToken();

        //3、设置访问报头，将2部获取到的token封装进报头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "bearer "+token);

        //4、设置报文
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

        //5、调用keyloak接口
        ResponseEntity<String> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);
        if(null!= responseEntity && null!= responseEntity.getBody()){
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            rets = gson.fromJson(responseEntity.getBody(), new TypeToken<List<GroupMembersDto>>(){}.getType());
            for (GroupMembersDto dto: rets){
                dto.setCnName(dto.getFirstName()+dto.getLastName());
            }
        }
        return rets;
    }

    @Override
    public UserRolesDto getUserRoles(String userId, String clientId) {
        UserRolesDto userRoles = new UserRolesDto();
        List<String> realmRoles = Lists.newArrayList();
        List<String> clientRoles = Lists.newArrayList();

        //1、设置访问keycloak的地址
        String url = getAuthBaseUrl()+"/admin/realms/"+getRealm()+"/users/"+userId+"/role-mappings";

        //2、获取访问keycloak所需token
        String token = getAccessToken();

        //3、设置访问报头，将2部获取到的token封装进报头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "bearer "+token);

        //4、设置报文
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

        //5、调用keyloak接口
        ResponseEntity<String> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);
        if(null!= responseEntity && null!= responseEntity.getBody()){
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            KeycloakRolesDto keycloakRolesDto = gson.fromJson(responseEntity.getBody(), KeycloakRolesDto.class);
            //6、封装公共角色
            for (PublicRolesDto dto: keycloakRolesDto.getRealmMappings()){
                if(dto.getName().startsWith("ROLE_")){
                    realmRoles.add(dto.getName());
                }
            }
            //7、封装私有角色
            Map<String, KeycloakClientRoleElementDto> maps = keycloakRolesDto.getClientMappings();
            if(null!= maps && null != maps.get(clientId)){
                KeycloakClientRoleElementDto keycloakClientRoleElementDto = maps.get(clientId);
                for(PublicRolesDto dto: keycloakClientRoleElementDto.getMappings()){
                    if(dto.getName().startsWith("ROLE_")){
                        clientRoles.add(dto.getName());
                    }
                }
                }
            }
        userRoles.setPublicRoles(realmRoles);
        userRoles.setPrivateRoles(clientRoles);
        return userRoles;
    }

    //获取keycloak base url
    private String getAuthBaseUrl(){
        try{
            return KeycloakUtil.getDeployment().getAuthServerBaseUrl();
        }catch (Exception e){
            LOG.error("getAuthBaseUrl error {}", e);
            return this.authUrl;
        }
    }

    //获取clientId
    private String getClientID(){
        try{
            return KeycloakUtil.getDeployment().getResourceName();
        }catch (Exception e){
            LOG.error("getClientID error {}", e);
            return this.clientId;
        }

    }

    //获取realm
    private String getRealm(){
        try{
            return KeycloakUtil.getDeployment().getRealm();
        }catch (Exception e){
            LOG.error("getRealm error {}", e);
            return this.realm;
        }
    }

    //获取secretId
    private String getSecretId(){
        try{
            return (String)KeycloakUtil.getDeployment().getResourceCredentials().get("secret");
        }catch (Exception e){
            LOG.error("getSecretId error {}", e);
            return this.secret;
        }
    }

    //获取经过处理的restTemplate
    private RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new LinkedList<HttpMessageConverter<?>>();
        // String --> UTF-8 --> 解决乱码问题
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter() {
            @Override
            protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException {
                String readInternal = super.readInternal(clazz, inputMessage);
                return new String(readInternal.getBytes(), getDefaultCharset());
            }
        };
        stringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        messageConverters.add(stringHttpMessageConverter);
        // Json --> UTF-8 --> 解决乱码问题
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        messageConverters.add(jackson2HttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    //使用迭代方法将keycloak返回的数据结构封装成pap的格式
    private void contertToPAP(List<GroupDto> groupDtos, Map<String, String> maps, List<GroupElementDto> rets){
        for(int i=0; i<groupDtos.size(); i++){
            maps.put(groupDtos.get(i).getName(), groupDtos.get(i).getId());
            GroupElementDto groupElementDto = new GroupElementDto();
            BeanUtils.copyProperties(groupDtos.get(i), groupElementDto);
            processGroupElementDto(groupDtos.get(i), groupElementDto, maps);
            rets.add(groupElementDto);
            contertToPAP(groupDtos.get(i).getSubGroups(), maps, rets);
        }
    }

    //处理父机构信息
    private void processGroupElementDto(GroupDto dto, GroupElementDto groupElementDto, Map<String, String> maps){
        String[] paths = dto.getPath().split("/");
        //如果层级为1，则无父级机构
        if(paths.length>2){
            //父机构名称
            String pName = paths[paths.length-2];
            //封装父机构信息
            groupElementDto.setpId(maps.get(pName));
            groupElementDto.setpName(pName);
        }
    }

}
