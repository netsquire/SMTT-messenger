package mtt.smtt.templates;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import mtt.smtt.Application;
import mtt.smtt.templates.FreemarkerConfiguration;
import freemarker.core.Environment;
import freemarker.template.Template;

public class TemplateEngine {
	
	public TemplateEngine(){}
	
	public String applyTemplate(Template tmpl, Map<String, Object> datum) {
		String str = null;
		try{
			StringWriter buff = new StringWriter();
			Environment env = tmpl.createProcessingEnvironment(datum, buff);
			env.setOutputEncoding(Application.getConfigurationProperty("TemplateCoding")); //"UTF-8");				
			env.process();			
			str = buff.getBuffer().toString();
			} catch (Exception e) { 	e.printStackTrace(); }
		return str;
	}
	
	public Template createTemplate(String templateFile) {
		Template tmpl = null;
		try { 
			tmpl = new FreemarkerConfiguration().getTemplate(templateFile);
		} catch (IOException e) {	e.printStackTrace();}
		return tmpl;
	}
}
