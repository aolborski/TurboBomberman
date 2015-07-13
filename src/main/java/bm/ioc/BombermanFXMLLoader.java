package bm.ioc;

import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component("fxmlLoader")
public class BombermanFXMLLoader extends FXMLLoader implements ApplicationContextAware {

  private static final Logger LOG = LoggerFactory.getLogger(BombermanFXMLLoader.class);

  public BombermanFXMLLoader() throws MalformedURLException {

    super(BombermanFXMLLoader.class.getResource("/bm/ui/RootPane.fxml"));
  }

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {

    LOG.debug("setting application context: {}", applicationContext);

    setControllerFactory(applicationContext::getBean);

  }
}

