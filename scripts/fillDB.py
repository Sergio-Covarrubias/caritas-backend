# fillDB.py
import requests
import random
from faker import Faker
from datetime import date, timedelta

fake = Faker()

BASE_URL = "http://localhost:8080/api"
WIPE_ENDPOINT = "/dev/wipe"
USERS_ENDPOINT = "/users"
PERSONS_ENDPOINT = "/persons"
HOSTELS_ENDPOINT = "/hostels"
SERVICES_ENDPOINT = "/services"
HOSTEL_SERVICES_ENDPOINT = "/hostel-services"
RESERVATIONS_ENDPOINT = "/reservations"

# ---------- Utilities ----------
def random_date(start: date, end: date) -> date:
    """Return a random date between start and end (inclusive)."""
    if start > end:
        raise ValueError("start date must be <= end date")
    days = (end - start).days
    return start + timedelta(days=random.randint(0, days))

def gen_phone_10_digits() -> str:
    return "".join(str(random.randint(0, 9)) for _ in range(10))

# ---------- Core actions ----------
def wipe_data():
    r = requests.post(BASE_URL + WIPE_ENDPOINT)
    r.raise_for_status()
    print("Wiped all data")

def create_users_with_persons(count=5):
    """Create `count` users. For each user create between 1 and 3 persons.
       Returns: (users_list, persons_by_user_dict)
    """
    users = []
    persons_by_user = {}

    for i in range(count):
        user_payload = {
            "id": fake.uuid4(),
            "firstName": fake.first_name(),
            "lastName": fake.last_name(),
            "email": fake.email(),
            "phoneNumber": gen_phone_10_digits()
        }
        r = requests.post(BASE_URL + USERS_ENDPOINT, json=user_payload)
        r.raise_for_status()
        created_user = r.json()
        users.append(created_user)
        uid = created_user["id"]
        persons_by_user[uid] = []
        print(f"Created user {uid}")

        num_persons = random.randint(1, 3)
        for _ in range(num_persons):
            # Generate a random birthdate between 1950 and 2022
            start_birth = date(1950, 1, 1)
            end_birth = date(2022, 12, 31)
            birth_date = random_date(start_birth, end_birth)

            person_payload = {
                "firstName": fake.first_name(),
                "lastName": fake.last_name(),
                "userId": uid,
                "birthDate": birth_date.strftime("%Y-%m-%d"),
                "alergies": [fake.word() for _ in range(random.randint(0, 3))],
                "discapacities": [fake.word() for _ in range(random.randint(0, 3))],
                "medicines": [fake.word() for _ in range(random.randint(0, 3))]
            }
            pr = requests.post(BASE_URL + PERSONS_ENDPOINT, json=person_payload)
            pr.raise_for_status()
            person_obj = pr.json()
            persons_by_user[uid].append(person_obj["id"])
            print(f"    ↳ Created person {person_obj['id']} for user {uid}")

    return users, persons_by_user

def create_services():
    services_data = [
        {"type": "hostel", "price": 30.0},
        {"type": "breakfast", "price": 15.0},
        {"type": "meal", "price": 15.0},
        {"type": "dinner", "price": 10.0},
        {"type": "laundry", "price": 10.0},
        {"type": "shower", "price": 10.0},
        {"type": "transportation", "price": 20.0},
        {"type": "psychological_check", "price": 0.0},
        {"type": "dental_check", "price": 0.0},
        {"type": "documents", "price": 5.0}
    ]
    results = []
    for s in services_data:
        r = requests.post(BASE_URL + SERVICES_ENDPOINT, json=s)
        r.raise_for_status()
        results.append(r.json())
    print(f"Created {len(results)} services")
    return results

def create_hostels():
    hostels_data = [
        {
            "name": "Posada del Peregrino",
            "description": "Este es el albergue Posada del Peregrino",
            "maxCapacity": 30,
            "locationUrl": "https://maps.app.goo.gl/cCnyrwYFLcMK6RHw6",
            "imageUrls": [
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nr7cjm7Qelam-mKgI5Q4-6KmZ2SdSw7a9Qs6CKRhp7uHzmCXF1efAEnL4aXyOcQ3dj63OhZcYE9Mj7zIBELJR0z0kRvAIyk5hlre-32KpaJ9x-cR2H29SOn-iXdbIiFupMtV6x0NQ=w203-h152-k-no",
                "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?output=thumbnail&cb_client=maps_sv.tactile.gps&panoid=ShVWyLi_jzrDtqXOe7uP9g&w=735&h=362&thumb=2&yaw=296.62482&pitch=0",
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nrXG4jTxTf5el85RzF2NYxsADpTo-9LS88tc5qDilMvh_uM3D-1psYFd5DXUHK_moTSPOowNIxyZrAWvinJ-I6EhDLrXqlLQL3mJnjNcq9YnaYdUc4XcvWdNN1PvMApLu5OxwcXXg=s660-k-no"
            ]
        },
        {
            "name": "Divina Providencia",
            "description": "Este es el albergue Divina Providencia",
            "maxCapacity": 30,
            "locationUrl": "https://maps.app.goo.gl/skRvdYhFNKpqXcdq9",
            "imageUrls": [ 
                "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=_oeMYgkm-Z0ANaoEc4hg3A&cb_client=search.gws-prod.gps&w=408&h=240&yaw=311.8523&pitch=0&thumbfov=100", 
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nqj3R9hETvjEo0fWgdKR6WNCVVo9i3jaYg4-9ywVGzneEHG4CmzmciHAjsITd1Ru7ObUO1mMKAsmcRYfislDrkrwQGNDqq9gXYtC_8SXm_OBdBUUzCwzDMgAf9BwTitqwHmrJidDQ=w408-h816-k-no", 
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4noP5BsVoA1HfogBqqahl5KdFB7FayFX5rxh94MB3riewOMhF7mr1kWaCR5l_hB1b8DYPEeYcdmWWEBFuiAVP3osdGRIVQTkGiw7uGhGG6ZTeWFV9P_qUX9_GQUc9wNFNW_yhaer8w=w426-h240-k-no" 
            ]
        },
        {
            "name": "Apodaca",
            "description": "Este es el albergue Apodaca",
            "maxCapacity": 30,
            "locationUrl": "https://maps.app.goo.gl/Y4mjL2uSjFEo2QAV7",
            "imageUrls": [
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nrCkuBshWtNW5N7KeQsnoQGxuzjlv8U8HqZyt83P0miQxo-e2-HyuH5L_HuzdA0T8VJ-3XLM0Xk0-pezy0Ol-vBYjfDY-zVefZuhDWrpg24B9-NHfYj2jahbQ7rNUA2wwKb1IzbKXos9INc=w203-h152-k-no",
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4np2TW_2xqnzEG5ryNEXKC9jsvXNVxMQy05AAz9z87zGYNRDXXs2wxPJU0CQI3ebd7i89rPy01e73hPQfQItEWYyMBHzFEuonS6Sf1WCV1HaXdY1RExR4_VfbHKO3HV2MEXtk-OR=w203-h360-k-no",
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nqFgOL6uvJNuEp9vaKEFifTHIw_ySNVYxu_bdrm47vt3Ey3-o7NLG3O2DkFFDdyUJ9BOEaDe0C-qWGarF_znSY6kUY7PPgWLyr-bsoYf3twFfUrufapzQ63LKGtmA04bFEmHgWw=w426-h240-k-no"
            ]
        }
    ]
    results = []
    for h in hostels_data:
        r = requests.post(BASE_URL + HOSTELS_ENDPOINT, json=h)
        r.raise_for_status()
        results.append(r.json())
    print(f"Created {len(results)} hostels")
    return results

def create_hostel_services(hostels, services):
    results = []
    for hostel in hostels:
        for service in services:
            payload = {"hostelId": hostel["id"], "serviceId": service["id"]}
            r = requests.post(BASE_URL + HOSTEL_SERVICES_ENDPOINT, json=payload)
            r.raise_for_status()
            results.append(r.json())
    print(f"Linked {len(results)} hostel-services (all hostels x all services)")
    return results

def create_reservations(users, persons_by_user, services, hostels):
    start_limit = date(2025, 10, 1)
    end_limit = date(2025, 11, 30)
    created_reservations = []

    for user in users:
        uid = user["id"]
        person_ids = persons_by_user.get(uid, [])
        if not person_ids:
            print(f"Skipping user {uid}: no persons")
            continue

        hostel = random.choice(hostels)
        hostel_id = hostel["id"]

        start_date = random_date(start_limit, end_limit)

        # 50% chance of having endDate = None
        if random.random() < 0.5:
            end_date = None
        else:
            end_date = random_date(start_date, end_limit)

        k = random.randint(1, min(5, len(services)))
        chosen_services = random.sample(services, k)
        service_ids = [s["id"] for s in chosen_services]

        reservation_payload = {
            "userId": uid,
            "hostelId": hostel_id,
            "startDate": start_date.strftime("%Y-%m-%d"),
            "endDate": end_date.strftime("%Y-%m-%d") if end_date else None,
            "personIds": person_ids,
            "serviceIds": service_ids
        }

        r = requests.post(BASE_URL + RESERVATIONS_ENDPOINT, json=reservation_payload)
        r.raise_for_status()
        reservation_obj = r.json()
        reservation_id = reservation_obj.get("id") or reservation_obj.get("reservationId") or reservation_obj.get("uuid") or reservation_obj
        created_reservations.append(reservation_obj)
        print(f"Created reservation {reservation_id} for user {uid} with {len(person_ids)} persons and {len(service_ids)} services (endDate={end_date})")

    return created_reservations

# ---------- Main ----------
def main():
    try:
        print("=== STARTING SEED SCRIPT ===")
        wipe_data()

        users, persons_by_user = create_users_with_persons(count=5)
        services = create_services()
        hostels = create_hostels()
        create_hostel_services(hostels, services)
        create_reservations(users, persons_by_user, services, hostels)

        print("=== SEEDING COMPLETE ===")
    except Exception as e:
        print("Error:", e)

if __name__ == "__main__":
    main()
