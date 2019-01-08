package net.galewski;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "metalink")
public class MetaLink {
    public  Date date = new Date();
    public List<File> file = new ArrayList<>();
}
