package com.example.kidquest.objects;


public class GetPrew {
    int id;
    byte[] image;
    //ArrayList<> imagelist= new ArrayList();

    public GetPrew(int id,byte[] image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
