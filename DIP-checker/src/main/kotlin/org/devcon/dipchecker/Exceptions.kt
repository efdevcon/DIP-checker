package org.devcon.dipchecker

class FolderMustExist(name: String) : Exception("folder $name does not exist")
class FoundExtraFileException(name: String) : Exception("found file $name which is not an md or the images directory")
class InvalidImageException(name: String) : Exception("Found file in images directory that does not seem to be an image: $name")
class InvalidHeaderStart(fileName: String) : Exception("Header must start with --- at $fileName")
class InvalidHeaderEnd(fileName: String) : Exception("Header must end with --- at $fileName")
class FirsHeaderMustBeDIPHeader : Exception("First header must be DIP header")
class ImagesMustBeDirectory : Exception("Images must be directory")
class MDMustStartWithDIPException : Exception("md file must start with DIP-")
class MDMustEndWithNUmber(name: String) : Exception("md end with a number - but was '$name'")
class DIPHeaderNumberDoesNotMatchFilename(fromFileName: String, fromHeader: String) : Exception("The DIP number in the filename ($fromFileName) must be the same in the Header ($fromHeader)")