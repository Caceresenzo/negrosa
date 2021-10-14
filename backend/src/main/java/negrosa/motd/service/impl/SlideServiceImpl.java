package negrosa.motd.service.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import negrosa.motd.dto.request.SlideUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;
import negrosa.motd.repository.SlideRepository;
import negrosa.motd.service.SlideService;

@Service
@Slf4j
public class SlideServiceImpl implements SlideService {
	
	public static final int SCALING = 4;
	
	public static final String ENTITY = "slide";
	
	@Autowired
	private SlideRepository repository;
	
	@Override
	public Optional<Slide> findById(long id) {
		return repository.findById(id);
	}
	
	@Override
	public List<Slide> create(Presentation presentation, InputStream input) throws IOException {
		Path presentationStoragePath = Paths.get("storage", "motd", "presentations", String.valueOf(presentation.getId()));
		Files.createDirectories(presentationStoragePath);
		
		try (XMLSlideShow slideShow = new XMLSlideShow(input)) {
			Dimension dimention = slideShow.getPageSize();
			dimention.setSize(dimention.getWidth() * SCALING, dimention.getHeight() * SCALING);
			
			slideShow.setPageSize(dimention);
			
			BufferedImage bufferedImage = new BufferedImage((int) dimention.getWidth(), (int) dimention.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.scale(SCALING, SCALING);
			
			List<XSLFSlide> xslfSlides = slideShow.getSlides();
			
			List<Slide> slides = new ArrayList<>(xslfSlides.size());
			
			long position = 0;
			for (XSLFSlide xslfSlide : xslfSlides) {
				graphics.setPaint(Color.WHITE);
				graphics.fill(new Rectangle2D.Double(0, 0, dimention.getWidth(), dimention.getHeight()));
				
				xslfSlide.draw(graphics);
				
				String file = presentationStoragePath.resolve(String.valueOf(position) + ".png").toString();
				try (FileOutputStream out = new FileOutputStream(file)) {
					ImageIO.write(bufferedImage, "png", out);
				}
				
				slides.add(new Slide()
						.setPresentation(presentation)
						.setPosition(++position)
						.setFile(file));
			}
			
			return repository.saveAll(slides);
		} catch (Throwable throwable) {
			try {
				Files.deleteIfExists(presentationStoragePath);
			} catch (Exception ignored) {
				log.warn("Could not delete directory", ignored);
			}
			
			throw throwable;
		}
	}
	@Override
	public Slide update(Slide slide, SlideUpdateRequest body) {
		boolean needSave = false;
		
		if (body.getDuration() != null && slide.getDuration() != body.getDuration()) {
			needSave = true;
			
			slide.setDuration(body.getDuration());
		}
		
		if (needSave) {
			return repository.save(slide);
		}
		
		return slide;
	}
	
	@Override
	public String getEntityName() {
		return ENTITY;
	}
	
}