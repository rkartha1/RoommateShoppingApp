If you live with roommates, you may have to purchase items, which are shared by everyone (e.g., everyone needs paper towels, water, and dishwasher detergent). It would be useful to have a
common place for listing shopping needs for such shared items, and then recording shopping expenses
(who bought which items, when, and what was the cost). In a nutshell, one of the roommates who is
going to a store could look at the current shopping list and then purchase some, or all items and record
the cost of the purchased items. The cost of the items purchased by all roommates over a time would
then be computed by the app and shared by all roommates, equally. Google’s Firebase should be used
to provide the basis for this app and enable easy sharing of the data. Some basic features of the app are
listed below:

• A roommate must register and login to use the shopping list app. Logging out should be
possible, as well.

• The shopping list must be shared by and be accessible to all participating roommates (you
should assume that the app is for just one apartment/household).

• A roommate must be able to add items to the list.

• A roommate currently in a store must be able to mark any item as purchased and enter its price. The item should be moved from the shopping list to a separate “recently purchased” list and store its price and record which roommate made the purchase.

          o Alternatively, a roommate in a store could select several items from the shopping list, mark them all as purchased, move all of them to the “recently purchased” list, and enter their total price (perhaps including tax), storing which roommate made the purchase.

• Any roommate must at some point be able to “settle the cost” and compute:

        o the total cost of the purchases in the apartment/household (by all roommates),
        
        o the average cost per roommate (the total cost divided by the number of roommates),
        
        o the total cost by roommate (money spent for purchases by each roommate).
        
Settling the cost should clear the “recently purchased” list. Optionally, your app may compute
the amounts that each roommate owes or is owed.

• After settling the cost, the roommates pay each other to settle their expense differences, but this is outside the scope of the app.
