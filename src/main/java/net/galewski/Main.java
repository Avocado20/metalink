package net.galewski;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Task {

    private String url;
    private String file;
    protected List<FileSet> filesets = new ArrayList();

    @Override
    public void execute() throws BuildException {
        super.execute();
        loadUrl();
        List<java.io.File> file = getFiles();
        List<File> metaLinkFiles = getMetaLinkFiles(file);
        MetaLink mainElement = new MetaLink();
        mainElement.file = metaLinkFiles;
        saveFile(mainElement);
    }

    protected void loadUrl() {
         setUrl(getProject().getProperty("server.files.url"));
    }

    public List<java.io.File> getFiles() {
        java.io.File folder = new java.io.File(".");
        java.io.File[] listOfFiles = folder.listFiles();
        return Arrays.asList(listOfFiles);
    }

    public void addFileset(FileSet fileset) {
        filesets.add(fileset);
    }

    public List<File> getMetaLinkFiles(List<java.io.File> files) {

        List<File> metaLinkFiles = new ArrayList<>();
        files.stream().forEach(p -> {
            File metaLinkFile = new File();
            metaLinkFile.name = p.getName();
            metaLinkFile.size = p.length();
            Hash hash = new Hash();
            hash.value = DigestUtils.md5Hex(p.getName());
            metaLinkFile.hash = hash;
            metaLinkFile.url = getUrl() + p.getName();
            metaLinkFiles.add(metaLinkFile);
        });
        return metaLinkFiles;
    }

    public void saveFile(MetaLink metaLink) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(MetaLink.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(metaLink, new java.io.File("zad-zwiwo.xml"));

        } catch (JAXBException e) {
            System.out.println(e.toString());
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
