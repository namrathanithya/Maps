package com.example.namra.mvvm_map_new.model;

import java.util.ArrayList;
import java.util.List;

public class Photo {

    //@SerializedName("height")
    //@Expose
    private Integer height;
    //@SerializedName("html_attributions")
   // @Expose
    private List<String> html_attributions = new ArrayList<String>();
   // @SerializedName("photo_reference")
   // @Expose
    private String photo_reference;
    //@SerializedName("width")
   // @Expose
    private Integer width;

    /**
     *
     * @return
     * The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The htmlAttributions
     */
    public List<String> getHtmlAttributions() {
        return html_attributions;
    }

    /**
     *
     * @param htmlAttributions
     * The html_attributions
     */
    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.html_attributions = htmlAttributions;
    }

    /**
     *
     * @return
     * The photoReference
     */
    public String getPhotoReference() {
        return photo_reference;
    }

    /**
     *
     * @param photoReference
     * The photo_reference
     */
    public void setPhotoReference(String photoReference) {
        this.photo_reference = photoReference;
    }

    /**
     *
     * @return
     * The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

}
