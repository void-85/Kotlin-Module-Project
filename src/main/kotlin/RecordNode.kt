enum class RecordType { Root ,
    File ,
    Note ,
    Text }

enum class ActionType { DeleteRecord ,
    CreateRecord ,
    SelectRecord ,
    Exit         }


class RecordNode {

    private val name :String
    private val type :RecordType
    private val data :ArrayList<RecordNode>



    constructor() {
        name = "root"
        type = RecordType.Root
        data = ArrayList<RecordNode>()
    }

    private constructor( name :String     ,
                         type :RecordType ) {
        this.name = name
        this.type = type
        this.data = ArrayList<RecordNode>()
    }



    private fun getChildItemsType() :RecordType {
        return when( this.type ) {
            RecordType.Root -> RecordType.File
            RecordType.File -> RecordType.Note
            RecordType.Note -> RecordType.Text
            RecordType.Text -> RecordType.Text // never happens
        }
    }



    private fun add( name :String ) {

        this.data.add( RecordNode( name, getChildItemsType() ) )

        // .Note can contain only 1 .Text element, so we delete 1st (old) .Text element
        if( this.type == RecordType.Note && this.data.size > 1 ) this.data.removeFirst()
    }



    fun recursiveCrawl() {

        loop@ while(true) {

            when(this.type) {
                RecordType.Root -> {}//println("\n")
                RecordType.File -> println("${ANSI.WHITE}Архив ${this.name}:")
                RecordType.Note -> println("${ANSI.WHITE}Заметка ${this.name}:")
                RecordType.Text -> {}//println("\n")
            }

            val ( actionType , actionValue ) = UI.printItemsAndRequestAction (
                this.data.map{ "${it.name}  (элементов:${it.data.size})" }.map{ it.replace("  (элементов:0)","") },
                getChildItemsType()
            )
            when( actionType ) {
                ActionType.CreateRecord -> add( UI.requestNewItemName( getChildItemsType() ) )
                ActionType.DeleteRecord -> data.removeAt( actionValue -1 )
                ActionType.SelectRecord -> this.data[ actionValue -1 ].recursiveCrawl()
                ActionType.Exit         -> break@loop
            }
        }
    }
}