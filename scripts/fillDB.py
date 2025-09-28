import requests

BASE_URL = "http://localhost:8080/api"
WIPE_ENDPOINT = "/dev/wipe"
USERS_ENDPOINT = "/users"
HOSTELS_ENDPOINT = "/hostels"
SERVICES_ENDPOINT = "/services"
HOSTEL_SERVICES_ENDPOINT = "/hostel-services"

def wipe_data():
    r = requests.post(BASE_URL + WIPE_ENDPOINT)
    r.raise_for_status()
    print("Wiped all data")

def create_user():
    payload = {
        "id": "123456",
        "firstName": "Sergio Alejandro",
        "lastName": "Covarrubias Cázares",
        "email": "example@gmail.com",
        "phoneNumber": "6444123456"
    }
    r = requests.post(BASE_URL + USERS_ENDPOINT, json=payload)
    r.raise_for_status()
    print("Created user")
    return r.json()

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
                "https://pixabay.com/get/gfbf0742d1384412e5817049021efcb7df31958be0f6c8bb4d6b62446d1b6572464e98b6f0ba04cb7b5f6da271ffafe7e25c30763531fd06c3e135ea948fc78b270c2c74f6c928b91f6ba81b5d6959cf6_640.jpg",
                "g4ac829c5370c8fd78e2cce6cb5af0c5dde876707f5d442d7142edaf85328a9daaf4328ab1159057632f94fc088ba229be3a421a425a376f144c644f5ce58485e137a86abb9efb89c73189e02e9d44baf_640"
            ]
        },
        {
            "name": "Apodaca",
            "description": "Este es el albergue Apodaca",
            "maxCapacity": 30,
            "locationUrl": "https://maps.app.goo.gl/Y4mjL2uSjFEo2QAV7",
            "imageUrls": [
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nqLzmmKaTb3Zs7ktgxH-ExIZYvJ1yJTkrfYXsWpBF8vGg6mgaZHnud2_mcsdze2jnlkvJQ272PEmWv0Lc7KR9dtNKtpLV3i-ADoRsLVFMHbwKiH5HjHFY6l7wrFPmJDOCzL260=w203-h114-k-no",
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nrCkuBshWtNW5N7KeQsnoQGxuzjlv8U8HqZyt83P0miQxo-e2-HyuH5L_HuzdA0T8VJ-3XLM0Xk0-pezy0Ol-vBYjfDY-zVefZuhDWrpg24B9-NHfYj2jahbQ7rNUA2wwKb1IzbKXos9INc=w203-h152-k-no",
                "https://lh3.googleusercontent.com/gps-cs-s/AC9h4np2TW_2xqnzEG5ryNEXKC9jsvXNVxMQy05AAz9z87zGYNRDXXs2wxPJU0CQI3ebd7i89rPy01e73hPQfQItEWYyMBHzFEuonS6Sf1WCV1HaXdY1RExR4_VfbHKO3HV2MEXtk-OR=w203-h360-k-no"
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
            payload = {
                "hostelId": hostel["id"],
                "serviceId": service["id"]
            }
            r = requests.post(BASE_URL + HOSTEL_SERVICES_ENDPOINT, json=payload)
            r.raise_for_status()
            results.append(r.json())
    print(f"Linked {len(results)} hostel-services (all hostels to all services)")
    return results

def main():
    try:
        print("=== STARTING SEED SCRIPT ===")
        wipe_data()

        user = create_user()
        print("User:", user)

        services = create_services()
        print("Services:", services)

        hostels = create_hostels()
        print("Hostels:", hostels)

        hostel_services = create_hostel_services(hostels, services)
        print("Hostel-Services:", hostel_services)

        print("=== SEEDING COMPLETE ===")

    except Exception as e:
        print("Error:", e)

if __name__ == "__main__":
    main()
