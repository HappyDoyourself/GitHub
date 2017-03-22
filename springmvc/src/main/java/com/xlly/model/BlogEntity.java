package com.xlly.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by fanhongtao
 * Date 2017-03-22 09:54
 */
@Entity
@Table(name = "blog", schema = "", catalog = "order")
public class BlogEntity {
    private int id;
    private String title;
    private String content;
    private Date pubDate;
    private Date updateDate;
    private UserEntity userById;
    private BlogEntity blogByUserId;
    private Collection<BlogEntity> blogsById;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, insertable = true, updatable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "pub_date", nullable = true, insertable = true, updatable = true)
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    @Basic
    @Column(name = "update_date", nullable = true, insertable = true, updatable = true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntity that = (BlogEntity) o;

        if (id != that.id) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (pubDate != null ? !pubDate.equals(that.pubDate) : that.pubDate != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUserById() {
        return userById;
    }

    public void setUserById(UserEntity userById) {
        this.userById = userById;
    }

    /*@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public BlogEntity getBlogByUserId() {
        return blogByUserId;
    }*/

    public void setBlogByUserId(BlogEntity blogByUserId) {
        this.blogByUserId = blogByUserId;
    }

    /*@OneToMany(mappedBy = "blogByUserId")
    public Collection<BlogEntity> getBlogsById() {
        return blogsById;
    }
*/
    public void setBlogsById(Collection<BlogEntity> blogsById) {
        this.blogsById = blogsById;
    }
}
