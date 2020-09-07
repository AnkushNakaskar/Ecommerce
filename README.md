# Ecommerce
* This project is about Shopping card with two perspective for Seller & User
* This has below feature
    * From Seller View
        * Seller can add/remove product in inventory
        * Seller can see all the product ,added by him/her
    * Customer View
        * Customer can add product to Cart
        * Customer can remove product from Cart
        * Customer can view cart details 
        * Customer can place order from Cart
        * Customer can see the previous orders
    * Admin View
        * Admin can see/add/remove all the seller
        * Admin can see/add/remove all the user
        * Admin can see all the product
        * Admin can see all the orders  
* Steps to run application
    * Checkout the project
    * Run below command 
        ```./gradlew clean bootRun```    
    * Import  ```Ecommerce.postman_collection.json```  into Postman
        * Follow below steps from API point of view to see the behaviour         
            * 'getAllProducts' : to see the all the existing product
            * 'Seller to Add new Product ' : to add new product from seller
            * 'add product (CART)' : To add product into Cart for User
            * 'Cart details' : get the current view of Cart
            * 'placeOrder(CART)' : Placed the order
                * Now you can check the all the product API, to see the effect
                * You can also see the cart view, if products are available after placing the order
* All application in developed using 'H2 Database' (In memory)
* Tables are same as : Entity classes of application.

* Application covered under Test cases for RestAPI & Junit.
    
* Improvements require : There are many things can be added here, like foreign key restriction.
                 
                 
