import java.util.Scanner


class UI {
    companion object {

        // for (x) number of items fun always adds
        // ( 0 ).NEW ITEM
        // (x+1).EXIT
        fun printItemsAndRequestAction( items :List<String> , itemsType :RecordType ): Pair<ActionType, Int> {

            when( itemsType ){
                RecordType.File -> println( "${ANSI.CYAN}Список архивов:  \n${ANSI.YELLOW}0.${ANSI.BLUE} Создать архив"             )
                RecordType.Note -> println( "${ANSI.CYAN}Список заметок:  \n${ANSI.YELLOW}0.${ANSI.BLUE} Создать заметку"           )
                RecordType.Text -> println(                                "${ANSI.YELLOW}0.${ANSI.BLUE} Изменить описание заметки" )
                else            -> {}
            }

            val exitCode :Int = items.size +1

            items.forEachIndexed { i, s -> println("${ANSI.YELLOW}${i + 1}.${ANSI.GREEN} ${s}") }

            println("${ANSI.YELLOW}${ exitCode }.${ANSI.RED} Выход")
            println()


            var selected :Int = exitCode +1
            while( !( -exitCode < selected && selected <= exitCode ) )
            {
                print("${ANSI.YELLOW}введите число (отрицательное - для удаления элемента): ")
                selected = Scanner(System.`in`).nextLine().toIntOrNull() ?: (exitCode +1)
            }
            println()

            // cannot crawl into .Text
            if( itemsType == RecordType.Text && selected == 1 && exitCode == 2 ) return Pair( ActionType.CreateRecord, 0 )

            return when{
                selected == exitCode -> Pair( ActionType.Exit, 0 )

                selected == 0 -> Pair( ActionType.CreateRecord, 0 )
                selected  < 0 -> Pair( ActionType.DeleteRecord, -selected )
                else          -> Pair( ActionType.SelectRecord, +selected )
            }
        }




        fun requestNewItemName( itemsType :RecordType ) :String {

            when( itemsType ){
                RecordType.File -> print("${ANSI.YELLOW}введите имя нового архива : ")
                RecordType.Note -> print("${ANSI.YELLOW}введите имя новой заметки : ")
                RecordType.Text -> print("${ANSI.YELLOW}введите новое описание заметки : ")
                else -> {}
            }

            var name = Scanner(System.`in`).nextLine()

            if( name == "" ) name = when( itemsType ) {
                RecordType.File -> "Безымянный архив"
                RecordType.Note -> "Безымянная заметка"
                RecordType.Text -> "Пустое описание"
                else -> ""
            }

            println()
            return name
        }
    }
}