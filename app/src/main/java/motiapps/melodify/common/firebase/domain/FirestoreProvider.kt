package motiapps.melodify.common.firebase.domain

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirestoreProvider @Inject constructor(
    private val firestore: FirebaseFirestore
){

    sealed interface CollectionType

    // region Collections Types

    sealed class RootCollection(val collectionName: String) : CollectionType {
        data object Users : RootCollection("users")
    }

    sealed class UserCollection(val userId: String, val collectionName: String) : CollectionType {
        class ScreenViews(userId: String) : UserCollection(userId, "screenViews")
    }

    // endregion

    // region Firestore References

    fun getUserReference(userId: String): DocumentReference {
        return firestore.collection(RootCollection.Users.collectionName).document(userId)
    }

    fun getCollection(collectionType: CollectionType): CollectionReference {
        return when (collectionType) {
            is RootCollection -> firestore.collection(collectionType.collectionName)
            is UserCollection -> firestore
                .collection(RootCollection.Users.collectionName)
                .document(collectionType.userId)
                .collection(collectionType.collectionName)
        }
    }

    // endregion
}