def group = 'org.svenehrke'
def version = '1.0.0-SNAPSHOT'
def pkg = "${group}"

Map map = [
  'client'  : 'dolphin-client-javafx-java',
  'combined': 'dolphin-combined-java',
  'server'  : 'dolphin-server-java',
  'shared'  : 'dolphin-shared-java',
]

map.each { k, v ->
  "lazybones create -Pgroup=${group} -Pversion=${version} -Ppackage=${pkg} $v $k".execute()
}
