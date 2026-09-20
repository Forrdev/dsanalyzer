 package com.sappyoak.dsanalyzer.game.files

 import com.sappyoak.dsanalyzer.game.GameEdition
 import com.sappyoak.dsanalyzer.game.Installation
 import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.withContext

 /**
  * Placeholder implementation.
  *
  * PTDE needs an archive header parser, which is a format what has not been implemented yet.
  * Remastered needs a directory walk, but is unreachable while installation inspection still rejects that edition.
  */
 public class ArchiveFileIndex : InstalledFileIndex {
     override suspend fun list(installation: Installation): FileListing = when (installation.build.edition) {
         GameEdition.PrepareToDie -> withContext(Dispatchers.IO) {
             openGameFiles(installation).use { FileListing.Hashed(it.hashes()) }
         }
         GameEdition.Remastered -> FileListing.Unsupported
     }
 }