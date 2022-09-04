package br.com.cvc.evaluation.fixtures;

import java.util.UUID;

import br.com.cvc.evaluation.domain.Profile;
import br.com.cvc.evaluation.domain.User;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UserFixture implements TemplateLoader {
    public static final String VALID = "valid";

    @Override
    public void load() {
        Fixture.of(User.class).addTemplate(VALID, new Rule() {
            {
                add("username", name());
                add("password", UUID.randomUUID().toString());
                add("profiles", has(3).of(Profile.class, ProfileFixture.VALID));
            }
        });
    }
}
