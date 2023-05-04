package org.devcon.dipchecker

import java.text.SimpleDateFormat

val imageExtensions = listOf("svg", "jpg", "jpeg", "png")

val mandatoryHeaders = listOf("DIP", "Title", "Status", "Themes", "Tags", "Authors", "Discussion", "Created")
val optionalHeaders = listOf("Resources Required", "Requires", "Instances")
val allHeaders = mandatoryHeaders + optionalHeaders
