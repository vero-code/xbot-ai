import logging
from flask import Flask, jsonify, request
from pytrends.request import TrendReq
from requests.exceptions import RequestException

app = Flask(__name__)

logging.basicConfig(level=logging.INFO)

@app.route('/trending', methods=['GET'])
def get_trending():
    country = request.args.get('country', 'united_states')
    logging.info(f"Fetching trends for country: {country}")

    try:
        pytrends = TrendReq(hl='en-US', tz=360)
        pytrends.build_payload(kw_list=[''])
        trending_searches = pytrends.trending_searches(pn=country)
        trends_list = trending_searches[0].tolist()

        logging.info(f"Successfully fetched trends for {country}")
        return jsonify(trends_list)

    except RequestException as e:
        logging.error(f"Failed to fetch trends: {e}")
        return jsonify({"error": "Failed to fetch trends", "details": str(e)}), 500
    except Exception as e:
        logging.error(f"Unexpected error: {e}")
        return jsonify({"error": "An unexpected error occurred", "details": str(e)}), 500

if __name__ == '__main__':
    app.run(port=5000)
