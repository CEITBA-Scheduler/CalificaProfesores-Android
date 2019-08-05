{
  "rules": {
    "OpinionesProf": {
      "$prof_id": {
        "$uid": {
          ".write": "auth.uid === $uid"
        }
      },
      ".indexOn": [
        "timestamp"
      ]
    },
    "OpinionesMaterias": {
      "$mat_id": {
        "$uid": {
          ".write": "auth.uid === $uid"
        }
      }
    },
    "Novedades": {
      ".read": "auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2' || auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'",
      ".write": "auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2' || auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'"
    },
    "ProfAddRequests": {
      "$uid": {
        ".read": "auth.uid === $uid || auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2'|| auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'",
        ".write": "auth.uid === $uid || auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2' || auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'"
      }
    },
    "ClassAddRequests": {
      "$uid": {
        ".read": "auth.uid === $uid || auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2'|| auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'",
        ".write": "auth.uid === $uid || auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2' || auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'"
      }
    },
    "UniAddRequests": {
      "$uid": {
        ".read": "auth.uid === $uid  || auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2' || auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'",
        ".write": "auth.uid === $uid || auth.uid === 'UEdLYqTMQWg57H3DLTbeqd17AlN2' || auth.uid === 'o2r4gfoLwHX0LkUnqoeQ5AC1qNQ2' || auth.uid === 'pcaEOfa3E5dXIKVWuslpVhcZd5o2'"
      }
    },
    "Prof": {
      ".indexOn": [
        "SearchName"
      ]
    },
    "Materias": {
      ".indexOn": [
        "Name"
      ]
    },
    "Facultades": {
      ".indexOn": [
        "Name",
        "CompleteName"
      ]
    },
    "SubjectSearchIndex": {
      ".write": "auth != null"
    },
    ".read": "auth != null",
    "UsersExtraData": {
      "$uid": {
        ".read": "auth != null",
        ".write": "auth.uid === $uid"
      }
    },
    "SearchWords": {
      ".indexOn": [
        "referenceWord"
      ],
      ".read": "auth != null",
      ".write": "auth.uid != null"
    }
  }
}
