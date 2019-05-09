package scalaDemo.ProgramingActors

import java.io.File

object CountFilesSequential extends App{

  def getChildre(file: File) = {
    val children = file.listFiles()
    if(children != null)
      children.toList
    else
      List()
  }
  val start = System.nanoTime()
  val fileName = "G:/bigdata"
  val exploreFrom = new File(fileName)
  var count = 0L
  var filesToVisit = List(exploreFrom)
  while(filesToVisit.nonEmpty){
    val head = filesToVisit.head
    filesToVisit = filesToVisit.tail
    val children = getChildre(head)
    count += children.count{ !_.isDirectory}
    filesToVisit = filesToVisit ::: children.filter(_.isDirectory)
  }
  val end = System.nanoTime()
  println(s"Number of files found: $count")
  println(s"Time taken: ${(end  - start) / 1.0e9} seconds")
}
