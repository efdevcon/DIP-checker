package org.devcon.dipchecker

val imageExtensions = listOf("svg", "jpg", "jpeg", "png")

val mandatoryHeaders = listOf("DIP", "Title", "Status", "Themes", "Tags", "Authors", "Discussion", "Created")
val optionalHeaders = listOf("Resources Required", "Requires")
val allHeaders = mandatoryHeaders + optionalHeaders