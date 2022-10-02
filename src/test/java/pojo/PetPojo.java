package pojo;

import java.util.List;

public class PetPojo {

    private int id;

    private String name;

    private List<String> photoUrls;

    private String status;

    private CategoryPojo category;

    private List<TagPojo> tags;

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CategoryPojo getCategory() {
        return category;
    }

    public void setCategory(CategoryPojo category) {
        this.category = category;
    }

    public List<TagPojo> getTags() {
        return tags;
    }

    public void setTags(List<TagPojo> tags) {
        this.tags = tags;
    }
}
