
import java.lang.NumberFormatException

fun main() {

    val coffeeMachine = CoffeeMachine()
    coffeeMachine.takeAction()

}

class CoffeeMachine {

    // initial stock [money, water, milk, beans, cups]
    private val stock = mutableListOf(550,400,540,120,9)

    // initial stock [money,water, milk, beans]
    private val expresso = mutableListOf(4,250,0,16)
    private val latte = mutableListOf(7,350, 75, 20)
    private val cappuccino = mutableListOf(6, 200, 100, 12) //

    fun takeAction() {

        while (true){
            println("\nWrite action (buy, fill, take, remaining, exit):")

            when (readln()) {
                "buy" -> buyCoffee()
                "fill" -> fillCoffeeMachine()
                "take" -> takeIncomeMoney()
                "remaining" -> coffeeMachineRemainingSupply()
                "exit" -> break
                else -> println("Invalid Operation")
            }
        }
    }

    private fun buyCoffee() {

        println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
        val choice = readln()
        if (choice == "back") {
            return
        }
        else {
            try {
                when(choice.toInt()) {
                    1 -> make(expresso)
                    2 -> make(latte)
                    3 -> make(cappuccino)
                    else -> println("Invalid input")
                }
            } catch (e: NumberFormatException) {
                println("Invalid input! please enter a valid number")
            }

        }

    }

    private fun coffeeMachineRemainingSupply() {

        println(""" 
            The coffee machine has:
            ${stock[1]} ml of water
            ${stock[2]} ml of milk
            ${stock[3]} g of coffee beans
            ${stock[4]} disposable cups
            ${'$'}${stock[0]} of money
        """.trimIndent())

    }

    private fun takeIncomeMoney() {
        println("I gave you $${stock[0]}")
        stock[0] = 0
    }


    private fun fillCoffeeMachine(){

        println("Write how many ml of water you want to add: ")
        stock[1] += readln().toInt()

        println("Write how many ml of milk you want to add: ")
        stock[2] += readln().toInt()

        println("Write how many grams of coffee beans you want to add: ")
        stock[3] += readln().toInt()

        println("Write how many disposable cups you want to add: ")
        stock[4] += readln().toInt()

    }

    /**
     * For one espresso, the coffee machine needs 250 ml of water and 16 g of coffee beans. It costs $4.
     ******************************************************************************************************************/
    private fun make(coffee: List<Int>) {

         if (stock[1] < coffee[1]) {
            println("Sorry, not enough water!")
        }
        else if (stock[2] < coffee[2]) {
            println("Sorry, not enough milk!")
        }
         else if (stock[3] < coffee[3]) {
            println("Sorry, not enough coffee beans!")

        } else {
             println("I have enough resources, making you a coffee!")
             stock[0] += coffee[0]
             stock[1] -= coffee[1]
             stock[2] -= coffee[2]
             stock[3] -= coffee[3]
             stock[4] -= 1
        }
    }




    private fun maxCapacityToMakeCoffee(): Int {
        val enoughWater = stock[1] / 200
        val enoughMilk = stock[2]  / 50
        val enoughCoffeeBeans = stock[3]  / 15

        // now get the lease amount from them.
        return findMinimum(enoughWater,enoughMilk,enoughCoffeeBeans)
    }

    private fun isAbleToMakeRequireCoffee(requireCups: Int) {

        val maxCapacityToMakeCoffee : Int = maxCapacityToMakeCoffee()

        when {
            requireCups == maxCapacityToMakeCoffee -> {
                println("Yes, I can make that amount of coffee")
            }
            requireCups > maxCapacityToMakeCoffee -> {
                println("No, I can make only $maxCapacityToMakeCoffee cups of coffee")
            }
            requireCups < maxCapacityToMakeCoffee.toInt() -> {
                println("Yes, I can make that amount of coffee (and even ${maxCapacityToMakeCoffee - requireCups} more than that)")
            }
        }
    }

    private fun findMinimum(vararg numbers: Int) = numbers.minOf { it }

    private fun readNumberOfCoffeeDrink(): Int {
        println("Write how many cups of coffee you will need:")
        return readln().toInt()
    }

    // NOT use for this stage
    fun coffeeMachineCapacity(){
        getWaterAmount()
        getMilkAmount()
        getCoffeeBeansAmount()

        val numberOfCoffeeCupRequired : Int = readNumberOfCoffeeDrink()
        isAbleToMakeRequireCoffee(numberOfCoffeeCupRequired)

    }

    /**
     * Figure out how much of each ingredient the machine will need.
     * Note that one cup of coffee made on this coffee machine contains
     * 200 ml of water, 50 ml of milk, and 15 g of coffee beans.
     ******************************************************************************************************************/
    private fun calculateIngredient(cups: Int) {
        println("For $cups cups of coffee you will need:")
        println("${cups * 200} ml of water")
        println("${cups * 50} ml of milk")
        println("${cups * 15} g of coffee beans")
    }

    /**
     * Get Water Amount from the user
     ******************************************************************************************************************/
    private fun getWaterAmount() {
        println("Write how many ml of water the coffee machine has:")
        stock[1] =  readln().toInt()
    }

    /**
     * Get Milk Amount from the user
     ******************************************************************************************************************/
    private fun getMilkAmount() {
        println("Write how many ml of milk the coffee machine has:")
        stock[2] = readln().toInt()
    }

    /**
     * Get Coffee beans amount
     ******************************************************************************************************************/
    private fun getCoffeeBeansAmount() {
        println("Write how many grams of coffee beans the coffee machine has:")
        stock[3] = readln().toInt()
    }
}