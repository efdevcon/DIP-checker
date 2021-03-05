package org.devcon.dipchecker

class FolderMustExist(name: String) : Exception("folder $name does not exist")
class FoundExtraFileException(name: String) : Exception("found file $name which is not an md or the images directory")
class InvalidImageException(name: String) : Exception("Found file in images directory that does not seem to be an image: $name")
class InvalidHeaderStart(fileName: String) : Exception("Header must start with --- at $fileName")
class HeaderNotClosed(fileName: String) : Exception("Header was never closed with --- at $fileName")
class InvalidHeaderException(header: String, fileName: String) : Exception("Invalid Header $header in $fileName")
class InvalidDateException(date: String, fileName: String) : Exception("Invalid Date $date in $fileName")
class MissingHeaderException(header: String, fileName: String) : Exception("Missing Header $header in $fileName")
class ImagesMustBeDirectory : Exception("Images must be directory")
class MDMustStartWithPrefixException(prefix: String, name: String) : Exception("md file must start with $prefix- but is $name")
class MDMustEndWithNUmber(name: String) : Exception("md end with a number - but was '$name'")
class DIPHeaderNumberDoesNotMatchFilename(fromFileName: String, fromHeader: String) :
    Exception("The DIP number in the filename ($fromFileName) must be the same in the Header ($fromHeader)")