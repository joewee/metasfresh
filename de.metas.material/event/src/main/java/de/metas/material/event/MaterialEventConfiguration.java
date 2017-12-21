package de.metas.material.event;

import java.util.Collection;

import org.compiere.Adempiere;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
@ComponentScan(basePackageClasses = {
		MaterialEventConfiguration.class,
		// StartupListener.class // there are different startup listener classes. one for metasfresh-backend, one for metasfresh-webui-api.
})
public class MaterialEventConfiguration
{
	@Bean(name = "materialEventService")
	@Profile(Profiles.PROFILE_Test)
	public MaterialEventService createLocalMaterialEventService(@NonNull final Collection<MaterialEventListener> listeners)
	{
		return MaterialEventService.createLocalServiceThatIsReadyToUse(listeners);
	}

	@Bean(name = "materialEventService")
	@DependsOn(Adempiere.BEAN_NAME)
	@Profile(value = { Profiles.PROFILE_App, Profiles.PROFILE_MaterialDispo })
	public MaterialEventService createDistributedMaterialEventService(@NonNull final Collection<MaterialEventListener> listeners)
	{
		final MaterialEventService materialEventService = MaterialEventService.createDistributedServiceThatNeedsToSubscribe(listeners);
		materialEventService.subscribeToEventBus();
		return materialEventService;
	}
}
