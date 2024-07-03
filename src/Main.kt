import java.util.*

// Contact class to represent each contact
data class Contact(val name: String, val phoneNumber: String, val email: String)

// AddressBook class to manage contacts
class AddressBook(private val capacity: Int) {
    private val contacts = arrayOfNulls<Contact>(capacity)
    private var numContacts = 0

    // Method to add a new contact to the address book
    fun addContact(name: String, phoneNumber: String, email: String) {
        if (numContacts < capacity) {
            contacts[numContacts] = Contact(name, phoneNumber, email)
            numContacts++
            println("Contact added successfully!")
        } else {
            println("Address book is full. Cannot add more contacts.")
        }
    }

    // Method to display all contacts in the address book
    fun displayContacts() {
        if (numContacts == 0) {
            println("Address book is empty.")
        } else {
            println("Listing all contacts:")
            contacts.filterNotNull().forEach {
                it.display()
            }
        }
    }

    // Method to search for a contact by name or phone number
    fun search(keyword: String) {
        var found = false
        contacts.filterNotNull().forEach {
            if (it.name.equals(keyword, ignoreCase = true) || it.phoneNumber == keyword) {
                println("Contact found:")
                it.display()
                found = true
                return
            }
        }
        if (!found) {
            println("Contact not found.")
        }
    }

    // Method to add multiple contacts at once
    fun addAll(newContacts: Array<Contact>) {
        val spaceLeft = capacity - numContacts
        if (newContacts.size > spaceLeft) {
            println("Not enough space to add all contacts.")
            return
        }
        newContacts.copyInto(contacts, numContacts, 0, newContacts.size)
        numContacts += newContacts.size
        println("Contacts added successfully!")
    }

    // Method to get the current size of the address book
    fun getSize(): Int {
        return numContacts
    }

    // Method to clear all contacts from the address book
    fun clear() {
        contacts.fill(null)
        numContacts = 0
        println("Address book cleared.")
    }

    // Method to remove a specific contact by name
    fun remove(name: String) {
        var found = false
        for (i in 0 until numContacts) {
            if (contacts[i]?.name.equals(name, ignoreCase = true)) {
                contacts[i] = null
                found = true
                break
            }
        }
        if (found) {
            contacts.filterNotNull().toTypedArray().copyInto(contacts)
            numContacts = contacts.size
            println("Contact removed successfully.")
        } else {
            println("Contact not found.")
        }
    }

    // Method to sort contacts by phone number
    fun sortByNumber() {
        contacts.filterNotNull().sortedBy { it.phoneNumber }.toTypedArray().copyInto(contacts)
        println("Contacts sorted by phone number.")
    }

    // Method to sort contacts by name
    fun sortByName() {
        contacts.filterNotNull().sortedBy { it.name }.toTypedArray().copyInto(contacts)
        println("Contacts sorted by name.")
    }

    // Method to display contact information
    private fun Contact.display() {
        println("Name: $name")
        println("Phone Number: $phoneNumber")
        println("Email: $email")
        println("--------------------")
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val addressBook = AddressBook(100) // Initialize with capacity for 100 contacts

    while (true) {
        println("Address Book Menu:")
        println("1. Add a new contact")
        println("2. Add multiple contacts")
        println("3. Display all contacts")
        println("4. Search for a contact by name or phone number")
        println("5. Remove a contact by name")
        println("6. Sort contacts by name")
        println("7. Sort contacts by phone number")
        println("8. Get size of address book")
        println("9. Clear address book")
        println("10. Exit")

        print("Enter your choice: ")
        when (val choice = scanner.nextInt()) {
            1 -> {
                println("Enter name: ")
                val name = scanner.nextLine()
                println("Enter phone number: ")
                val phoneNumber = scanner.nextLine()
                println("Enter email: ")
                val email = scanner.nextLine()
                addressBook.addContact(name, phoneNumber, email)
            }
            2 -> {
                println("Enter number of contacts to add: ")
                val numContactsToAdd = scanner.nextInt()
                scanner.nextLine()
                val newContacts = Array(numContactsToAdd) {
                    println("Contact ${it + 1}:")
                    println("Enter name: ")
                    val name = scanner.nextLine()
                    println("Enter phone number: ")
                    val phoneNumber = scanner.nextLine()
                    println("Enter email: ")
                    val email = scanner.nextLine()
                    Contact(name, phoneNumber, email)
                }
                addressBook.addAll(newContacts)
            }
            3 -> addressBook.displayContacts()
            4 -> {
                println("Enter name or phone number to search: ")
                val keyword = scanner.nextLine()
                addressBook.search(keyword)
            }
            5 -> {
                println("Enter name to remove: ")
                val removeName = scanner.nextLine()
                addressBook.remove(removeName)
            }
            6 -> addressBook.sortByName()
            7 -> addressBook.sortByNumber()
            8 -> println("Current size of address book: ${addressBook.getSize()}")
            9 -> addressBook.clear()
            10 -> {
                println("Exiting address book. Goodbye!")
                scanner.close()
                return
            }
            else -> println("Invalid choice. Please enter a number between 1 and 10.")
        }
        println() // Add a blank line for readability
    }
}
