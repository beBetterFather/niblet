package com.encdata.corn.niblet.dto.blog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName BlogRemarkDto
 * @Description mapper parameter type
 * @Author SiweiJin
 * @Date 2019/8/23 14:26
 * @Version 1.0
 **/
@Setter
@Getter
@ToString
public class BlogRemarkDto {

    private List<String> remarks;

}
