package com.encdata.corn.niblet.domain;

import java.io.Serializable;

/**
 * blog_tag
 * @author 
 */
public class BlogTag implements Serializable {
    /**
     * 文章id
     */
    private Long contentId;

    /**
     * 标签类型 1 白色 2 绿色
     */
    private Boolean type;

    /**
     * 标签
     */
    private String tag;

    private static final long serialVersionUID = 1L;

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BlogTag other = (BlogTag) that;
        return (this.getContentId() == null ? other.getContentId() == null : this.getContentId().equals(other.getContentId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getContentId() == null) ? 0 : getContentId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", contentId=").append(contentId);
        sb.append(", type=").append(type);
        sb.append(", tag=").append(tag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}