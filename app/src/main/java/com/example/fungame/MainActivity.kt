package com.example.fungame

// ...


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fungame.ui.theme.FunGameTheme

import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.random.Random

@Composable
fun OnboardingScreen(
    onContinueClicked:  () ->Unit,
    modifier: Modifier = Modifier) {
    // TODO: This state should be hoisted
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Column(
        //modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 0.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    FunGameTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            FunGameTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}


data class CardVal(val name: String, val suit: String)

fun generateRandomIntegers(
    n: Int,
    rangeStart: Int = 2,
    rangeEnd: Int = 14
): Array<Int> {
    return List(n) { Random.nextInt(rangeStart, rangeEnd + 1) }.toTypedArray()
    }

fun generateSuit(
    n: Int,
    rangeStart: Int = 1,
    rangeEnd: Int = 4
): Array<String> {

    var suitArr = arrayOf("clubs","hearts","spades","diamonds")

    return List(n) { suitArr[Random.nextInt(rangeStart, rangeEnd + 1) - 1] }.toTypedArray()
}

fun generateRandomCardVal(n : Int): Array<CardVal>
{
    var mappingFun: (Int) -> String = {num -> intToString(num) }
    val names = generateRandomIntegers(n).map(mappingFun);
    val suit = generateSuit(n);

    val cardvals = Array<CardVal>(n){i->CardVal(names[i],suit[i])}

    return cardvals;
}

fun intToString(n: Int): String {
    val stringArray = arrayOf("jack","queen","king","ace")
    if(n > 0 && n <=10) {
        return "$n"
    }
    else return stringArray[n-11];
}

fun evaluateMathExpression(expression: String): Double? {
    return try {
        val exp = ExpressionBuilder(expression).build()
        exp.evaluate()
    } catch (e: Exception) {
        null
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier){

    var mappingFun: (Int) -> String = {num -> intToString(num) }
    var shouldShowOnboarding by remember {mutableStateOf(true)}
    var text by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(false) }
    val newItems = generateRandomCardVal(4)//generateRandomIntegers(4).map(mappingFun)

    var mylist = remember { mutableStateListOf(CardVal("2","clubs")) }
    mylist.clear()
    mylist.addAll(newItems)

    Surface(modifier)
      {
          Column(modifier = Modifier.padding((24.dp))) {

                   /*OnboardingScreen(onContinueClicked = {
                       val newItems = generateRandomCardVal(4)//generateRandomIntegers(4).map(mappingFun)
                       mylist.clear()
                       mylist.addAll(newItems)
                   })

                    */

                  if(result) {
                      Text(
                          text = "correct ",
                      )
                  }

                  Greetings(
                      names=mylist,
                      text=text,
                      onTextChange = { text = it},
                      onClick = {
                          result = ( (24.0 == evaluateMathExpression(text)) )
                      },
                      onClickContinue = {
                          val newItems = generateRandomCardVal(4)//generateRandomIntegers(4).map(mappingFun)
                          mylist.clear()
                          mylist.addAll(newItems)
                      }
                      )
          }
      }
    }

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<CardVal> = listOf(CardVal("2","clubs")),
    text:String,
    onTextChange:(String)->Unit,
    onClick: ()->Unit,
    onClickContinue: ()->Unit
) {

    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier.width(250.dp),
        textStyle = TextStyle(
            fontSize = 20.sp // 👈 Change font size here
        )

    )
    Row(modifier = Modifier.padding((8.dp))) {

        Button(onClick = onClick) {
            Text("Check")
        }
        Button(onClick = onClickContinue) {
            Text("Continue")
        }
    }



    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier.padding(4.dp)
    ) {
        items(items = names) { name ->
            Card(name = name.name.toString(), suit = name.suit)
        }
    }


    /*Column(modifier = modifier.padding(vertical = 4.dp, horizontal = 25.dp)) {
        for (name in names) {
            Card(name = name)
        }
    }*/
}


fun getCardResId(rank: String, suit: String, context: Context): Int {
    val resourceName  =  "card${rank}_of_${suit}";
    val imageResource =  context.resources.getIdentifier(resourceName, "drawable", context.packageName);
    return imageResource;
}

/*fun getImageName(name: String): Int
{
    getResources().getIdentifier(name, "id", getPackageName());
    return 0;
}*/

@Composable
fun Card(name: String, suit:String="clubs", modifier: Modifier = Modifier) {
    val expanded = remember {mutableStateOf(false)}
    val extrapadding = if (expanded.value) 48.dp else 0.dp
    val idv = getCardResId(name,suit, context = LocalContext.current )
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical=0.dp,horizontal=12.dp)) {
        Row(modifier = Modifier.padding((8.dp))) {
            Column(modifier = modifier.padding(bottom = extrapadding))

            {
                Image(
                    //#painterResource(id=R.drawable.card2_of_clubs),
                    painterResource(id=idv),
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )

                /*Text(
                    text = "Hello ",
                )

                Text(text = "       $name")*/
            }

        }
    }
}



@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    FunGameTheme {
       // Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    FunGameTheme {
        MyApp(Modifier.fillMaxSize())
    }
}