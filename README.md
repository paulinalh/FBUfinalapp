# FBUfinalapp
 Final project for Facebook Univeristy summer 2021 internship
 
 Original App Design Project - README Template - Paulina Lopez Holguin
 ===
 
 # Where To Inclusion App
 
 ## Table of Contents
 1. [Overview](#Overview)
 1. [Product Spec](#Product-Spec)
 1. [Wireframes](#Wireframes)
 2. [Schema](#Schema)
 
 ## Overview
 ### Description
 App where users can search places in the city and check how inclusive and accesible the place is (ramps, elevator, door measurements, sign language training/ client service, menus or signals in braille). Users are also able to rate the quality of these inclusive infrastructure and services.
 
 ### App Evaluation
 [Evaluation of your app across the following attributes]
 - **Category:** Informative/Accesibility
 - **Mobile:**
 - **Story:**
 - **Market:** Mainly people with dissabilities, but can be used by the general public.
 - **Habit:** Users can search easily a place to go out and make sure it attends their needs, as a person with or without a dissability
 - **Scope:** An easy to use searcher for all the community of people with dissabilities in the city, and hopefully it grows to more and more places. Also focus on making it attractive for people without dissabilities, because more people is needed for better rating.
 
 ## Product Spec
 
 ### 1. User Stories (Required and Optional)
 
 **Required Must-have Stories**
 
 * user can create account and login/logout
 * user accounts database
 * mainscreen with searcher of type of place they want to go (each place has a tag of their type to facilitate the search)
 * card view screen with all the places of the search
 * click on each place and show details (general info and accesibility/inclusiveness)
     * In here the Google maps sdk
 * comment page where people can levae opinion of each place
     * Simple chat type of activity
 * Rate all the things of accesibility/inclusive
 * Rate service, food, time to wait and other details
 * User profile 
 
 **Optional Nice-to-have Stories**
 
 * the searcher shows the best places to go on top dependeing on accesibility and inclusion needs and nearness
 * Make it more attractive to the general public
     * give more details of the place
     * create a randomizer for which places to go
     * games for people to use the app (like a buzzfeed quiz of which place to go)
 * Have weekly recommendations for the user depending on the places they have visited
 
 ### 2. Screen Archetypes
 
 * Login screen
    * User can login
    * If the user doesnt have an account button to go to Sign up creen
 * Sign up screen
    * User can create new account 
    * New user registers into database
 * Home/discover screen
     * User can see places in the city as recommendations
 * Search screen
     * user search where to go (restuarant, mall, store, nail/hrair spa, coffee shop, ...)
     * or search for a specific place to knowhow accesible or inclusive a place is
 * Search Feed
     * card views of all the places that appear from their search
 * Details fragments
     * click on the place of the feed and show the details of the place
 
 ### 3. Navigation
 
 **Tab Navigation** (Tab to Screen)
 
 * Home (new places to visit, user can click for details and have recommendations)
 * Searcher tab
 * Profile tab
 
 **Flow Navigation** (Screen to Screen)
 
 * Login screen
    * -> login button goes to searcher screen
    * -> sign up button goes to sign up screen
 * Sign up screen
    * -> sign up button creates new account -> redirects to login screen
 * Home/discover fragment
     * Random recommendations of places they could visit
 * Search fragment
     * -> search for type or name of place -> shows feed of places
 * Search feed fragment
     * -> clicks on a specific place and opens details
 * Details fragments
     * details page has multiple tabs
         * Tab #1: Name of place, address, images, type of place, info, google maps and accesible infrastructre and inclusion services (not detailed)
         * Tab #2: rates all the accesibilty infrastrcuture and inclusion services from the place
         * Tab # 3: rates the the services of the place (food, service, parking, time to wait etc.)
         * Tab #4: detailed review or comment from people that went to the place
 
 
 ## Wireframes
 
 <img src="https://i.imgur.com/2qxjyH6.jpg" width=750>
 
 ### [BONUS] Digital Wireframes & Mockups
 
 ### [BONUS] Interactive Prototype
 
 ## Schema 
 
 ### Models
 
 Place
 
 | Property | Type | Description |
 | -------- | -------- | -------- |
 | comment     | pointer to comment         | All the comments a post have, made by users    |
 | title     | String     | name of the place     |
 | image     | File     | images of the place     |
 | address     | String     | address of the place     |
 | id     | String     | Google Places id for the place     |
 | services     | Array     | main services of the place     |
 | inclusiveServices     | Array     | all the inclusive services the place offers  |
 | createdAt | DateTime |date and time when the place post was created|
 |updatedAt | DateTime | date and time when the place post was updated | 
 
 Review
 
 | Property | Type | Description |
 | -------- | -------- | -------- |
 | text | String | paragraph of the opinion of the user |
 |user | pointer to User | user that made the review |
 | rating | int | rating of the service given by user |
 | createdAt | DateTime |date and time when the review was created|
 |updatedAt | DateTime | date and time when the review was updated | 
 
 User
 | Property | Type | Description |
 | -------- | -------- | -------- |
 | name | String | name displayed on user's profile |
 | username | String | unique username |
 |password | String | password to login |
 |picture | File | profile picture |
 |createdAt | String | mail registered to the account |
 |needs | Array | list of accessibility or inclusive services the user needs in each place|
 | createdAt | DateTime |date and time when the review was created|
  |updatedAt | DateTime | date and time when the review was updated | 
  
 
  Favorite
  | Property | Type | Description |
  | -------- | -------- | -------- |
  | placeId | String | id of the place |
  | imageUrl | String | google place's image url for the place |
  |name | String | name of place |
  | createdAt | DateTime |date and time when the favorite was created|
   |updatedAt | DateTime | date and time when the favorite was updated | 
   
  
Post
| Property | Type | Description |
| -------- | -------- | -------- |
| text | String | review of the user |
| user | ParseUser | unique username |
|likes | int | every like registered by users |
|image | ParseFile | image the user uploads |
| createdAt | DateTime |date and time when the post was created|
 |updatedAt | DateTime | date and time when the post was updated | 
 
 
 ### Networking
 * Login Screen
     * (Read/GET) authenticate user
 * Sign up Screen
     * (Create/POST) create new user
 * Home/discover screen
     * (Read/GET) query new places as recommendations
 * Search Screen
     * (Create/POST) make new search
 * Specific search feed screen
     * (Read/GET) Query all posts that have the specific inclusive services the user needs
 * Place details fragment
     * (Read/GET) Place information and details
     * (Update/PUT) Update the rating of the services of the place
 * Comment fragment in Place details fragment
     * (Create/POST) Create a new comment 
     * (Delete) Delete existing comment
 * Profile Screen
     * (Read/GET) Get user's information/profile image
     * (Update/PUT) Update user profile image
 
 
 - [Create basic snippets for each Parse network request]
 - [OPTIONAL: List endpoints if using existing API such as Yelp]
