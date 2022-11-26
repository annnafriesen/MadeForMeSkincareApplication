# My Personal Project

## MadeForMe Skincare 

This project is the design of a self-guided marketing platform called 
**MadeForMe Skincare**. With the intention of being implemented in online shopping
sites, this program will allow users to create a unique skincare 
routine by answering a questionnaire and selecting suggested items
of interest to add to their shopping cart. The application will filter the user's results 
to recommend products that address the users individual needs. 

The design of this program is targeted to those familiar with skincare products as well as 
those who are not; by answering simple questions, MadeForMe allows everyone 
to buy the right products for their skin. To model how this program would
be used by a skincare company, I will be using The Ordinary brand as a database
as it is inexpensive and accessible. Please note that MadeForMe can be used to recommend
a number of different brands individually or in combination with others. 

Growing up, I've realized that skincare can be complicated, not to mention a 
source of vulnerability for young adults. I've tried a number of different products 
before finding the right fit, so I'm creating a program that will remove the 
excess time and money in this process so others can feel confident in their skin from day one.

### User Stories

- As a user, I want to be directed to products that target my skin type and needs.
- As a user, I want to add products to my shopping cart.
- As a user, I want to access the description and ingredients of a product recommended to me.
- As a user, I want to remove a product from my shopping cart.
- As a user, I want to have a wishlist of items I can't afford at the present time.
- As a user, I want to be presented with the option to save my questionnaire answers, shopping cart, recommendation
list and wishlist to file.
- As a user, when I start the application, I want to be given the option to load my questionnaire answers, shopping 
cart, recommendation list and wishlist from file.

### Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by clicking the "Add" button to add a product
to your shopping cart.
- You can generate the second required event related to adding Xs to a Y by clicking the "Remove" button to remove a
product from your shopping cart.
- You can locate my visual component by clicking the "View" button to view product details. This will display the
Ordinary logo image. As well, if the shopper's max price is reached, a dialog box with an image of an overflowing 
shopping cart will appear alongside a message alerting the user that their max price has been reached. 
- You can save the state of my application by clicking the "Save" button on the shopping cart
panel.
- You can reload the state of my application by clicking the "Load" button on the shopping
cart panel. The shopping cart panel must empty in order for you to load a file.

### Phase 4: Task 2
**Sample event log**:

Fri Nov 25 12:13:02 PST 2022
Set shopper's name: Anna

Fri Nov 25 12:13:02 PST 2022
Set shopper's price: $50.00

Fri Nov 25 12:13:02 PST 2022
Set shopper's skin type: OILY

Fri Nov 25 12:13:02 PST 2022
Set shopper's concern: HYPERPIGMENTATION

Fri Nov 25 12:13:04 PST 2022
Added product: Natural Moisturizer

Fri Nov 25 12:13:05 PST 2022
Added product: Squalance Cleanser

Fri Nov 25 12:13:06 PST 2022
Removed product: Squalance Cleanser

### Phase 4: Task 2
Future refactor changes:
- Implement the Singleton Design Pattern in the MadeForMeSkinCareAppGUI and ShoppingCartUI. Right now, the Shopping cart
takes in the MadeForMeSkinCareAppGUI's shopping cart instantiation and then assigns a field to this reference this
object, but when debugging I found that there is a second object created. This would get rid of the issue of having to 
empty the shopping cart panel before loading from the JSON file. 
- Eliminate semantic coupling between the ShoppingCartUI and the MadeForMeSkinCareGUI. I would create an abstract class 
that ShoppingCartUI and MadeForMeSkinCareUI would extend, and this abstract class would contain similar fields between 
the two classes, such as shoppingCart, shopper and the View Button. Some methods that I would pull into the abstract
class would be the ViewProduct action, as both classes implement this field with minor differences in their
implementation. 
- To increase cohesion, I would extract some methods from the MadeForMeSkinCareAppGUI to make the class less long 
and more readable. For example, I might make an "Actions" class where I put all the actions used in the 
MadeForMeSkinCareAppUI. As well, to ensure that button commands also appear in the same format in the console app, I 
could extract a method called "buttonInfoFormat" where it takes in the button command and prints the command in the 
format "Type '" + COMMAND + "' load shopping cart from file". This would ensure all button commands are printed out in
a consistent way which would increase readability of the code.