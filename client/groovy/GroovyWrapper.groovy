/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Wrap a script and groovy jars to an executable jar
 */
def cli = new CliBuilder()
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.d( longOpt: 'destfile', argName: 'destfile', required: false, args: 1, 'jar destintation filename, defaults to {mainclass}.jar' )
cli.m( longOpt: 'mainclass', argName: 'mainclass', required: true, args: 1, 'fully qualified main class, eg. HelloWorld' )
cli.c( longOpt: 'groovyc', required: false, 'Run groovyc' )

//--------------------------------------------------------------------------
def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
  cli.usage();
  return
}

def mainClass = opt.m
def scriptBase = mainClass.replace( '.', '/' )
def scriptFile = new File( scriptBase + '.groovy' )
if (!scriptFile.canRead()) {
   println "Cannot read script file: '${scriptFile}'"
   return
}
def destFile = scriptBase + '.jar'
if (opt.d) {
  destFile = opt.d
}

//--------------------------------------------------------------------------
def ant = new AntBuilder()

if (opt.c) {
  ant.echo( "Compiling ${scriptFile}" )
  org.codehaus.groovy.tools.FileSystemCompiler.main( [ scriptFile ] as String[] )
}

def GROOVY_HOME = new File( System.getenv('GROOVY_HOME') )
if (!GROOVY_HOME.canRead()) {
  ant.echo( "Missing environment variable GROOVY_HOME: '${GROOVY_HOME}'" )
  return
}

ant.jar( destfile: destFile, compress: true, index: true ) {
  fileset( dir: '.', includes: scriptBase + '*.class' )

  zipgroupfileset( dir: GROOVY_HOME, includes: 'embeddable/groovy-all-*.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/commons*.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/http-builder-0.7.3-20140519.065827-1.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/appengine-api-1.0-sdk-1.3.8.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/commons-beanutils-1.8.0.jar')
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/commons-codec-1.6.jar')
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/commons-collections-3.2.1.jar')
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/commons-lang-2.4.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/commons-logging-1.1.1.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/ezmorph-1.0.6.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/httpclient-4.2.1.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/httpcore-4.2.1.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/json-lib-2.3-jdk15.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/nekohtml-1.9.16.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/signpost-commonshttp4-1.2.1.1.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/signpost-core-1.2.1.1.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/xercesImpl-2.9.1.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/xml-apis-1.3.04.jar' )
  zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/xml-resolver-1.2.jar' )
    
  // add more jars here

  manifest {
    attribute( name: 'Main-Class', value: mainClass )
  }
}

ant.echo( "Run script using: \'java -jar ${destFile} ...\'" )