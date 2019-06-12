
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.EducationDataRepository;
import domain.EducationData;

@Component
@Transactional
public class StringToEducationDataConverter implements Converter<String, EducationData> {

	@Autowired
	private EducationDataRepository	educationDataRepository;


	@Override
	public EducationData convert(final String text) {
		EducationData result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.educationDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
