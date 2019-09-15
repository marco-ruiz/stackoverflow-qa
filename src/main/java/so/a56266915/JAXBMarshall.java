package so.a56266915;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class JAXBMarshall {
	
	private JAXBContext jaxbContext;
	private Marshaller jaxbMarshaller;

	public JAXBMarshall() throws JAXBException {
		jaxbContext = JAXBContext.newInstance(ImageCapList.class);
		jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	}

	public void imageCapsMarshal(List<ImageCap> imageCaps, File outFile) {
		try {
			jaxbMarshaller.marshal(new ImageCapList(imageCaps), outFile);
		} catch (JAXBException e) {
			// HANDLE EXCEPTIONS
		}
	}

	
	public static void main(String[] args) throws JAXBException {
		JAXBMarshall jaxbMarshaller = new JAXBMarshall();

		File file = new File("file.xml");
		List<ImageCap> imageCaps = IntStream.range(0, 10)
				.mapToObj(idx -> new ImageCap("my/file/path/" + idx, idx + ". The Caption!"))
				.collect(Collectors.toList());
		jaxbMarshaller.imageCapsMarshal(imageCaps, file);
	}

	@XmlRootElement(name = "myImageCapList")
	static class ImageCapList {
		@XmlElement
		List<ImageCap> imageCap;

		public ImageCapList() {}

		public ImageCapList(List<ImageCap> imageCaps) {
			this.imageCap = imageCaps;
		}
	}

	@XmlRootElement
	static class ImageCap {
		@XmlElement
		String filePath;

		@XmlElement
		String caption;
		
		public ImageCap() {}
		
		public ImageCap(String filePath, String caption) {
			this.filePath = filePath;
			this.caption = caption;
		}
		
		@Override
		public String toString() {
			return "ImageCap{" + "filePath=" + filePath + ", caption=" + caption + '}';
		}
	}
}
