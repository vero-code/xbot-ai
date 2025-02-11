from flask import Flask, jsonify
from pytrends.request import TrendReq
from requests.exceptions import RequestException

app = Flask(__name__)

@app.route('/trending', methods=['GET'])
def get_trending():
    try:
        pytrends = TrendReq(hl='en-US', tz=360)
        pytrends.build_payload(kw_list=[''])
        trending_searches = pytrends.trending_searches(pn='united_states')
        trends_list = trending_searches[0].tolist()

        return jsonify(trends_list)

    except RequestException as e:
        return jsonify({"error": "Failed to fetch trends", "details": str(e)}), 500
    except Exception as e:
        return jsonify({"error": "An unexpected error occurred", "details": str(e)}), 500

if __name__ == '__main__':
    app.run(port=5000)
