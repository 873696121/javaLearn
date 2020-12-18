package huhong.pojo;

import java.util.List;

public class Category {
    private Integer id;
    private String name;
    private String url;
    private List<Category> subCategories;
    private List<Entity> subEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Entity> getSubEntities() {
        return subEntities;
    }

    public void setSubEntities(List<Entity> subEntities) {
        this.subEntities = subEntities;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", subCategory=" + subCategories +
                ", list=" + subEntities +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
