package bookhandler;

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties("application") // <1>
public class ApplicationConfigurationProperties implements ApplicationConfiguration {
    protected final Integer DEFAULT_BATCH_LENGTH = 10;
    @NotNull
    private Integer max = DEFAULT_BATCH_LENGTH;
    @Override
    public @NotNull Integer getMax() {
        return max;
    }
    public void setMax(@NotNull Integer max) {
        this.max = max;
    }
}
