 package com.sappyoak.dsanalyzer.game.files

 import com.sappyoak.dsanalyzer.game.GameEdition
 import com.sappyoak.dsanalyzer.game.Installation

 /**
  * Placeholder implementation.
  *
  * PTDE needs an archive header parser, which is a format what has not been implemented yet.
  * Remastered needs a directory walk, but is unreachable while installation inspection still rejects that edition.
  */
 public class ArchiveFileIndex : InstalledFileIndex {
     override suspend fun list(installation: Installation): FileListing = when (installation.build.edition) {
         GameEdition.PrepareToDie -> FileListing.Unsupported
         GameEdition.Remastered -> FileListing.Unsupported
     }
 }