package com.encdata.corn.niblet.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.keycloak.adapters.HttpClientBuilder;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.authentication.ClientCredentialsProviderUtils;
import org.keycloak.adapters.authorization.PolicyEnforcer;
import org.keycloak.adapters.rotation.HardcodedPublicKeyLocator;
import org.keycloak.adapters.rotation.JWKPublicKeyLocator;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.common.enums.SslRequired;
import org.keycloak.common.util.PemUtils;
import org.keycloak.enums.TokenStore;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.SystemPropertiesJsonParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.Map;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description keycloak配置处理类
 * @Author Siwei Jin
 * @Date 2018/10/26 13:31
 */
@Service
public class KeycloakConfigResolverNew implements org.keycloak.adapters.KeycloakConfigResolver {

    private static final Logger LOG = LoggerFactory.getLogger(KeycloakConfigResolverNew.class);

    @Value(value = "${keycloak.realm}")
    private String realm;

    @Value(value = "${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value(value = "${keycloak.ssl-required}")
    private String sslRequired;

    @Value(value = "${keycloak.resource}")
    private String resource;

    @Value(value = "${keycloak.credentials.secret}")
    private String credentialsSecret;

    @Value(value = "${keycloak.use-resource-role-mappings}")
    private Boolean useResourceRoleMappings;


    protected KeycloakDeployment deployment = new KeycloakDeployment();

    private  AdapterConfig adapterConfig;

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        adapterConfig = loadAdapterConfig(null);
        return internalBuild(adapterConfig);
    }

    protected AdapterConfig loadAdapterConfig(InputStream is) {
        AdapterConfig adapterConfig = new AdapterConfig();
        ObjectMapper mapper = new ObjectMapper(new SystemPropertiesJsonParserFactory());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        try {
            if(null!=is){
                adapterConfig = (AdapterConfig)mapper.readValue(is, AdapterConfig.class);
            }
            adapterConfig.setRealm(realm);
            adapterConfig.setAuthServerUrl(authServerUrl);
            adapterConfig.setSslRequired(sslRequired);
            adapterConfig.setResource(resource);
            Map<String, Object> credentials = Maps.newHashMap();
            credentials.put("secret", credentialsSecret);
            adapterConfig.setCredentials(credentials);
            adapterConfig.setUseResourceRoleMappings(useResourceRoleMappings);
            return adapterConfig;
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    protected KeycloakDeployment internalBuild(AdapterConfig adapterConfig) {
        if (adapterConfig.getRealm() == null) {
            throw new RuntimeException("Must set 'realm' in config");
        } else {
            this.deployment.setRealm(adapterConfig.getRealm());
            String resource = adapterConfig.getResource();
            if (resource == null) {
                throw new RuntimeException("Must set 'resource' in config");
            } else {
                this.deployment.setResourceName(resource);
                String realmKeyPem = adapterConfig.getRealmKey();
                if (realmKeyPem != null) {
                    try {
                        PublicKey realmKey = PemUtils.decodePublicKey(realmKeyPem);
                        HardcodedPublicKeyLocator pkLocator = new HardcodedPublicKeyLocator(realmKey);
                        this.deployment.setPublicKeyLocator(pkLocator);
                    } catch (Exception var6) {
                        throw new RuntimeException(var6);
                    }
                } else {
                    JWKPublicKeyLocator pkLocator = new JWKPublicKeyLocator();
                    this.deployment.setPublicKeyLocator(pkLocator);
                }

                if (adapterConfig.getSslRequired() != null) {
                    this.deployment.setSslRequired(SslRequired.valueOf(adapterConfig.getSslRequired().toUpperCase()));
                } else {
                    this.deployment.setSslRequired(SslRequired.EXTERNAL);
                }

                if (adapterConfig.getTokenStore() != null) {
                    this.deployment.setTokenStore(TokenStore.valueOf(adapterConfig.getTokenStore().toUpperCase()));
                } else {
                    this.deployment.setTokenStore(TokenStore.SESSION);
                }

                if (adapterConfig.getPrincipalAttribute() != null) {
                    this.deployment.setPrincipalAttribute(adapterConfig.getPrincipalAttribute());
                }

                this.deployment.setResourceCredentials(adapterConfig.getCredentials());
                this.deployment.setClientAuthenticator(ClientCredentialsProviderUtils.bootstrapClientAuthenticator(this.deployment));
                this.deployment.setPublicClient(adapterConfig.isPublicClient());
                this.deployment.setUseResourceRoleMappings(adapterConfig.isUseResourceRoleMappings());
                this.deployment.setExposeToken(adapterConfig.isExposeToken());
                if (adapterConfig.isCors()) {
                    this.deployment.setCors(true);
                    this.deployment.setCorsMaxAge(adapterConfig.getCorsMaxAge());
                    this.deployment.setCorsAllowedHeaders(adapterConfig.getCorsAllowedHeaders());
                    this.deployment.setCorsAllowedMethods(adapterConfig.getCorsAllowedMethods());
                }

                this.deployment.setBearerOnly(adapterConfig.isBearerOnly());
                this.deployment.setAutodetectBearerOnly(adapterConfig.isAutodetectBearerOnly());
                this.deployment.setEnableBasicAuth(adapterConfig.isEnableBasicAuth());
                this.deployment.setAlwaysRefreshToken(adapterConfig.isAlwaysRefreshToken());
                this.deployment.setRegisterNodeAtStartup(adapterConfig.isRegisterNodeAtStartup());
                this.deployment.setRegisterNodePeriod(adapterConfig.getRegisterNodePeriod());
                this.deployment.setTokenMinimumTimeToLive(adapterConfig.getTokenMinimumTimeToLive());
                this.deployment.setMinTimeBetweenJwksRequests(adapterConfig.getMinTimeBetweenJwksRequests());
                this.deployment.setPublicKeyCacheTtl(adapterConfig.getPublicKeyCacheTtl());
                if (realmKeyPem == null && adapterConfig.isBearerOnly() && adapterConfig.getAuthServerUrl() == null) {
                    throw new IllegalArgumentException("For bearer auth, you must set the realm-public-key or auth-server-url");
                } else {
                    if (realmKeyPem == null || !this.deployment.isBearerOnly() || this.deployment.isEnableBasicAuth() || this.deployment.isRegisterNodeAtStartup() || this.deployment.getRegisterNodePeriod() != -1) {
                        this.deployment.setClient((new HttpClientBuilder()).build(adapterConfig));
                    }

                    if (adapterConfig.getAuthServerUrl() != null || this.deployment.isBearerOnly() && realmKeyPem != null) {
                        this.deployment.setAuthServerBaseUrl(adapterConfig);
                        if (adapterConfig.getTurnOffChangeSessionIdOnLogin() != null) {
                            this.deployment.setTurnOffChangeSessionIdOnLogin(adapterConfig.getTurnOffChangeSessionIdOnLogin().booleanValue());
                        }

                        PolicyEnforcerConfig policyEnforcerConfig = adapterConfig.getPolicyEnforcerConfig();
                        if (policyEnforcerConfig != null) {
                            this.deployment.setPolicyEnforcer(new PolicyEnforcer(this.deployment, adapterConfig));
                        }

                        LOG.debug("Use authServerUrl: " + this.deployment.getAuthServerBaseUrl() + ", tokenUrl: " + this.deployment.getTokenUrl() + ", relativeUrls: " + this.deployment.getRelativeUrls());
                        return this.deployment;
                    } else {
                        throw new RuntimeException("You must specify auth-server-url");
                    }
                }
            }
        }
    }
}
