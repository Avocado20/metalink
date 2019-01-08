package net.galewski;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "file")
public class File {

    public String name;
    public Long size;
    public Hash hash = new Hash();
    public String url;
}
