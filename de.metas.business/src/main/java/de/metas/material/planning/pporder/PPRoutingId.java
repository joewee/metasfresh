package de.metas.material.planning.pporder;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PPRoutingId implements RepoIdAware
{
	@JsonCreator
	public static PPRoutingId ofRepoId(final int repoId)
	{
		return new PPRoutingId(repoId);
	}

	public static PPRoutingId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PPRoutingId(repoId) : null;
	}

	public static Optional<PPRoutingId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(final PPRoutingId PPOrderId)
	{
		return toRepoIdOr(PPOrderId, -1);
	}

	public static int toRepoIdOr(final PPRoutingId PPOrderId, final int defaultValue)
	{
		return PPOrderId != null ? PPOrderId.getRepoId() : defaultValue;
	}

	int repoId;

	private PPRoutingId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Workflow_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
