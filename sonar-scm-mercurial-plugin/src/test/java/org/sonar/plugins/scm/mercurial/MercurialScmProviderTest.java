/*
 * SonarQube :: Plugins :: SCM :: Mercurial
 * Copyright (C) 2014-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.scm.mercurial;

import java.io.File;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MercurialScmProviderTest {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void sanityCheck() {
    assertThat(new MercurialScmProvider(mockBlameCommand()).key()).isEqualTo("hg");
  }

  @Test
  public void testAutodetection() throws IOException {
    File baseDirEmpty = temp.newFolder();
    assertThat(new MercurialScmProvider(mockBlameCommand()).supports(baseDirEmpty)).isFalse();

    File tfsBaseDir = temp.newFolder();
    forceMkdir(new File(tfsBaseDir, ".hg"));
    assertThat(new MercurialScmProvider(mockBlameCommand()).supports(tfsBaseDir)).isTrue();
  }

  private static MercurialBlameCommand mockBlameCommand() {
    return mock(MercurialBlameCommand.class);
  }

}
