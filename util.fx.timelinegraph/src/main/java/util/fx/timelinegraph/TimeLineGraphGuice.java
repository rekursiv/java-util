package util.fx.timelinegraph;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import util.logging.console.ConsoleFormatter;

public class TimeLineGraphGuice extends AbstractModule {

	protected final EventBus eventBus = new EventBus();
	
	@Override
	protected void configure() {
		setupLogging();
		setupEventBus();
	}
	
	protected void setupEventBus() {
		bind(EventBus.class).toInstance(eventBus);
		bindListener(Matchers.any(), new TypeListener() {
			public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
				typeEncounter.register(new InjectionListener<I>() {
					public void afterInjection(I i) {
						eventBus.register(i);
//						System.out.println("EventBus registered "+i.getClass().getName());
					}
				});
			}
		});
	}
	
	protected void setupLogging() {
		LogManager.getLogManager().reset();
		
		// setup logging to console
		Handler ch = new ConsoleHandler();
		ch.setFormatter(new ConsoleFormatter());
		ch.setLevel(Level.ALL);
		LogManager.getLogManager().getLogger("").addHandler(ch);
	}

}
