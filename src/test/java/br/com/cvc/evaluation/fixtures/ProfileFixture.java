package br.com.cvc.evaluation.fixtures;

import br.com.cvc.evaluation.domain.Profile;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ProfileFixture implements TemplateLoader {
    public static final String VALID = "valid";
    @Override
    public void load() {
        Fixture.of(Profile.class).addTemplate(VALID, new Rule(){
            {
                add("id", random(Integer.class, range(1, 999)));
                add("name", random("admin", "user", "manager"));
            }
        });
    }
}
