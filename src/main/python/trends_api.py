from flask import Flask, jsonify
from pytrends.request import TrendReq

app = Flask(__name__)

@app.route('/trending', methods=['GET'])
def get_trending():
    pytrends = TrendReq(hl='en-US', tz=360)
    pytrends.build_payload(kw_list=[''])
    trending_searches = pytrends.trending_searches(pn='united_states')
    trends_list = trending_searches[0].tolist()

    return jsonify(trends_list)

if __name__ == '__main__':
    app.run(port=5000)
