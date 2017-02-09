package net.ximias;

public class Webapp{
    private String name;
    private String path;
    public Webapp(String name,String path){
        this.name=name;
        this.path=path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
