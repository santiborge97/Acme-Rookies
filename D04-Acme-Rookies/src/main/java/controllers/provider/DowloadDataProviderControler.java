
package controllers.provider;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.MessageService;
import services.ProviderService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Message;
import domain.Provider;
import domain.SocialProfile;

@Controller
@RequestMapping("/data/provider")
public class DowloadDataProviderControler extends AbstractController {

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void test(final HttpSession session, final HttpServletResponse response) throws IOException {

		final String language = LocaleContextHolder.getLocale().getLanguage();

		if (language == "en") {
			String myString = "Below these lines you can find all the data we have at Acme-Rookies:\r\n";

			final Provider p = this.providerService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(p.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(p.getId());

			myString += "\r\n\r\n";

			myString += p.getName() + p.getSurnames() + " " + p.getProviderMake() + " " + p.getAddress() + " " + p.getEmail() + " " + p.getPhone() + " " + p.getPhoto() + " \r\n";
			myString += "\r\n\r\n";
			myString += "Social Profiles:\r\n";
			for (final SocialProfile s : sc)
				myString += s.getNick() + " " + s.getLink() + " " + s.getSocialName() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Messages:\r\n\r\n";
			for (final Message msg : msgs)
				myString += "Sender: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Recipient: " + msg.getRecipient().getName() + " " + msg.getRecipient().getSurnames() + " Moment: " + msg.getMoment() + " Subject: "
					+ msg.getSubject() + " Body: " + msg.getBody() + " Tags: " + msg.getTags();
			myString += "\r\n\r\n";

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=my_data_as_provider.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();

		} else {
			String myString = "Debajo de estas lineas puedes encontrar todos los datos que tenemos de ti en Acme-Rookies:\r\n";

			final Provider p = this.providerService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(p.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(p.getId());

			myString += "\r\n\r\n";

			myString += p.getName() + " " + p.getSurnames() + " " + p.getProviderMake() + " " + p.getAddress() + " " + p.getEmail() + " " + p.getPhone() + " " + p.getPhoto() + " \r\n";
			myString += "\r\n\r\n";
			myString += "Perfiles Sociales:\r\n";
			for (final SocialProfile s : sc)
				myString += s.getNick() + " " + s.getLink() + " " + s.getSocialName() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Mensajes:\r\n\r\n";
			for (final Message msg : msgs)
				myString += "Emisor: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Receptor: " + msg.getRecipient().getName() + " " + msg.getRecipient().getSurnames() + " Momento: " + msg.getMoment() + " Asunto: "
					+ msg.getSubject() + " Cuerpo: " + msg.getBody() + " Etiquetas: " + msg.getTags();
			myString += "\r\n\r\n";

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=mis_datos_como_proveedor.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();
		}
	}
}
