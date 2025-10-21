import argparse
import os
from dotenv import load_dotenv
import requests
import random
from faker import Faker
from datetime import date, timedelta

fake = Faker()

load_dotenv(dotenv_path=os.path.join(os.path.dirname(__file__), '..', '.env'))

session = requests.Session()
AUTH_HEADERS = {}

parser = argparse.ArgumentParser(description="Seed database with dummy data")
parser.add_argument("--prod", action="store_true", help="Use production signup flow")
args = parser.parse_args()

IS_PROD = args.prod

BASE_URL = "http://localhost:8080/api"
LOGIN_ENDPOINT = "/admin/login"
WIPE_ENDPOINT = "/dev/wipe"
USERS_ENDPOINT = "/users"
PERSONS_ENDPOINT = "/admin/persons"
HOSTELS_ENDPOINT = "/admin/hostels"
SERVICES_ENDPOINT = "/admin/services"
HOSTEL_SERVICES_ENDPOINT = "/admin/hostel-services"
RESERVATIONS_ENDPOINT = "/dev/reservations"
SERVICE_RESERVATIONS_ENDPOINT = "/admin/service-reservations"
CONFIRM_SERVICE_RESERVATIONS_ENDPOINT = "/admin/service-reservations/confirm"

if IS_PROD:
    ONLINE_URL = os.getenv("ONLINE_URL")
    if not ONLINE_URL:
        raise ValueError("ONLINE_URL environment variable not found in .env file")
    BASE_URL = ONLINE_URL

# ---------- Utilities ----------
def random_date(start: date, end: date) -> date:
    """Return a random date between start and end (inclusive)."""
    if start > end:
        raise ValueError("start date must be <= end date")
    days = (end - start).days
    return start + timedelta(days=random.randint(0, days))

def gen_phone_number() -> str:
    return "+52" + "".join(str(random.randint(0, 9)) for _ in range(10))

# ---------- Core actions ----------
def login_admin():
    """Logs in the admin and sets the Authorization header for all requests."""
    admin_email = os.getenv("ADMIN_EMAIL")
    admin_password = os.getenv("ADMIN_PASSWORD")

    if not admin_email or not admin_password:
        raise ValueError("ADMIN_EMAIL and ADMIN_PASSWORD must be set in .env")

    print("Logging in as admin...")

    payload = {"email": admin_email, "password": admin_password}
    url = f"{BASE_URL}{LOGIN_ENDPOINT}"

    r = session.post(url, json=payload)
    r.raise_for_status()
    data = r.json()

    id_token = data.get("idToken")
    if not id_token:
        raise ValueError("No idToken returned in admin login response")

    # Set global header for all future requests
    AUTH_HEADERS["Authorization"] = f"Bearer {id_token}"
    session.headers.update(AUTH_HEADERS)

    print("Admin login successful.")

def wipe_data():
    r = session.post(f"{BASE_URL}{WIPE_ENDPOINT}?prod={'true' if IS_PROD else 'false'}")
    r.raise_for_status()
    print("Wiped all data")

def create_users_with_persons(count):
    users = []
    persons_by_user = {}

    if IS_PROD:
        # Fetch existing users from the production backend
        print("Fetching existing users from backend...")
        base = BASE_URL.rstrip("/")
        r = session.get(f"{base}{USERS_ENDPOINT}")
        r.raise_for_status()
        users = r.json()

        if not users:
            raise RuntimeError("No users found in production system!")

        print(f"Fetched {len(users)} existing users")
        # Initialize the mapping
        for user in users:
            persons_by_user[user["id"]] = []

    else:
        # Local mode: create fake users
        for i in range(count):
            phone = gen_phone_number()
            first_name = fake.first_name()
            last_name = fake.last_name()

            user_payload = {
                "id": fake.uuid4(),
                "firstName": first_name,
                "lastName": last_name,
                "phoneNumber": phone
            }

            r = session.post(BASE_URL + USERS_ENDPOINT, json=user_payload)
            r.raise_for_status()
            created_user = r.json()
            users.append(created_user)
            persons_by_user[created_user["id"]] = []
            print(f"Created user {created_user['id']} (LOCAL)")

    # Add persons for each user (both local and prod)
    for user in users:
        uid = user["id"]
        num_persons = random.randint(1, 3)
        for _ in range(num_persons):
            start_birth = date(1950, 1, 1)
            end_birth = date(2022, 12, 31)
            birth_date = random_date(start_birth, end_birth)

            ALERGIAS = ["polen", "gluten", "lácteos", "mariscos", "cacahuates"]
            DISCAPACIDADES = ["visual", "auditiva", "motora", "intelectual", "del habla"]
            MEDICINAS = ["paracetamol", "ibuprofeno", "omeprazol", "insulina", "loratadina"]

            person_payload = {
                "firstName": fake.first_name(),
                "lastName": fake.last_name(),
                "userId": uid,
                "birthDate": birth_date.strftime("%Y-%m-%d"),
                "alergies": random.sample(ALERGIAS, random.randint(0, 2)),
                "discapacities": random.sample(DISCAPACIDADES, random.randint(0, 2)),
                "medicines": random.sample(MEDICINAS, random.randint(0, 2))
            }

            pr = session.post(BASE_URL + PERSONS_ENDPOINT, json=person_payload)
            pr.raise_for_status()
            person_obj = pr.json()
            persons_by_user[uid].append(person_obj["id"])
            print(f"    ↳ Created person {person_obj['id']} for user {uid}")

    return users, persons_by_user

def create_services():
    services_data = [
        {"type": "breakfasts", "price": 15.0},
        {"type": "meals", "price": 15.0},
        {"type": "dinners", "price": 10.0},
        {"type": "laundries", "price": 10.0},
        {"type": "baths", "price": 10.0},
        {"type": "transportations", "price": 20.0},
        {"type": "mentals", "price": 0.0},
        {"type": "dentals", "price": 0.0},
        {"type": "documents", "price": 5.0}
    ]
    results = []
    for s in services_data:
        r = session.post(BASE_URL + SERVICES_ENDPOINT, json=s)
        r.raise_for_status()
        results.append(r.json())
    print(f"Created {len(results)} services")
    return results

def create_hostels():
    hostels_data = [
        {
            "name": "Posada del Peregrino",
            "description": "Este es el albergue Posada del Peregrino",
            "price": 30.0,
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
            "price": 30.0,
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
            "price": 30.0,
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
        r = session.post(BASE_URL + HOSTELS_ENDPOINT, json=h)
        r.raise_for_status()
        results.append(r.json())
    print(f"Created {len(results)} hostels")
    return results

def create_hostel_services(hostels, services):
    results = []
    for hostel in hostels:
        for service in services:
            payload = {"hostelId": hostel["id"], "serviceId": service["id"]}
            r = session.post(BASE_URL + HOSTEL_SERVICES_ENDPOINT, json=payload)
            r.raise_for_status()
            results.append(r.json())
    print(f"Linked {len(results)} hostel-services")
    return results

def create_service_reservations_for_reservation(reservation, services):
    """Create 3 service reservations for a given reservation."""
    reservation_id = reservation["id"]
    start_date_str = reservation.get("startDate")
    end_date_str = reservation.get("endDate")

    # Convert to date objects
    start_date = date.fromisoformat(start_date_str)
    if end_date_str:
        end_date = date.fromisoformat(end_date_str)
    else:
        # if no end_date, make one 3–5 days after start_date
        end_date = start_date + timedelta(days=random.randint(3, 5))

    for _ in range(3):
        service = random.choice(services)
        service_name = service["type"]
        order_date = random_date(start_date, end_date)
        count = random.randint(1, 5)

        payload = {
            "reservationId": str(reservation_id),
            "serviceName": service_name,
            "orderDate": order_date.strftime("%Y-%m-%d"),
            "count": count,
            "state": "PENDING"
        }

        r = session.post(BASE_URL + SERVICE_RESERVATIONS_ENDPOINT, json=payload)
        r.raise_for_status()
        service_res = r.json()
        service_res_id = service_res.get("id") or service_res.get("uuid") or service_res

        print(f"    ↳ Created service reservation {service_res_id} ({service_name}) for reservation {reservation_id}")

        # 50% chance to confirm the service reservation
        if random.random() < 0.5:
            confirm_url = f"{BASE_URL}{CONFIRM_SERVICE_RESERVATIONS_ENDPOINT}/{service_res_id}"
            rc = session.post(confirm_url)
            rc.raise_for_status()
            print(f"        ↳ Confirmed service reservation {service_res_id}")

def create_reservations(users, persons_by_user, hostels, services):
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
        end_date = random_date(start_date, end_limit) if random.random() > 0.5 else None

        # 50% chance for ACTIVE or PENDING
        state = "ACTIVE" if random.random() < 0.5 else "PENDING"
    
        reservation_payload = {
            "userId": uid,
            "hostelId": hostel_id,
            "startDate": start_date.strftime("%Y-%m-%d"),
            "endDate": end_date.strftime("%Y-%m-%d") if end_date else None,
            "personIds": person_ids,
            "state": state
        }

        r = session.post(BASE_URL + RESERVATIONS_ENDPOINT, json=reservation_payload)
        r.raise_for_status()
        reservation_obj = r.json()
        reservation_id = reservation_obj.get("id") or reservation_obj
        created_reservations.append(reservation_obj)
        print(f"Created {state} reservation {reservation_id} for user {uid} (endDate={end_date})")

        # Only create service reservations if state is ACTIVE
        if state == "ACTIVE":
            create_service_reservations_for_reservation(
                reservation_obj, 
                [s for s in services if s["type"] not in ("transportations")]
            )

    return created_reservations

def create_past_reservations(users, persons_by_user, hostels, services):
    start_limit = date(2025, 8, 10)
    end_limit = date(2025, 9, 30)
    created_reservations = []

    for user in users:
        uid = user["id"]
        person_ids = persons_by_user.get(uid, [])
        if not person_ids:
            continue

        for _ in range(random.randint(3, 4)):
            hostel = random.choice(hostels)
            hostel_id = hostel["id"]
            start_date = random_date(start_limit, end_limit)
            end_date = random_date(start_date, end_limit)
            state = random.choice(["INACTIVE", "CANCELLED"])

            reservation_payload = {
                "userId": uid,
                "hostelId": hostel_id,
                "startDate": start_date.strftime("%Y-%m-%d"),
                "endDate": end_date.strftime("%Y-%m-%d"),
                "personIds": person_ids,
                "state": state
            }

            r = session.post(BASE_URL + RESERVATIONS_ENDPOINT, json=reservation_payload)
            r.raise_for_status()
            reservation_obj = r.json()
            reservation_id = reservation_obj.get("id") or reservation_obj
            created_reservations.append(reservation_obj)
            print(f"Created {state} reservation {reservation_id} for user {uid}")

            # Create related service reservations
            create_service_reservations_for_reservation(reservation_obj, [s for s in services if s["type"] not in ("transportations")])

    return created_reservations

# ---------- MAIN ----------
def main():
    try:
        print("=== STARTING SEED SCRIPT ===")

        if IS_PROD:
            login_admin()

        wipe_data()

        users, persons_by_user = create_users_with_persons(count=8)
        services = create_services()
        hostels = create_hostels()
        create_hostel_services(hostels, services)
        create_reservations(users, persons_by_user, hostels, services)
        create_past_reservations(users, persons_by_user, hostels, services)

        print("=== SEEDING COMPLETE ===")
    except Exception as e:
        print("Error:", e)

if __name__ == "__main__":
    main()
