package mtt.smtt.templates;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfiguration {

	String pathToTemplates = "src/main/resources/templates";
	private Configuration config;
	
	public Configuration getConfig(){ return this.config;}
	
	public FreemarkerConfiguration() throws IOException {
		config = new Configuration();
		File templatesDir = new File(pathToTemplates);
		config.setDirectoryForTemplateLoading(templatesDir);
		config.setDefaultEncoding("UTF-8");

		// Sets how errors will appear.
		// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER); 		
		}

	public Template getTemplate(String templateFile) {
		Template template = null;
		try {
			template = config.getTemplate(templateFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}
	
}
