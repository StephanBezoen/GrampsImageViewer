import SwiftUI
import shared

struct ContentView: View {
    let type:AssetType
    let datex:Kotlinx_datetimeInstant
    let asset:Asset
    init() {
        type = AssetType.image
        datex = Kotlinx_datetimeInstant.Companion().fromEpochMilliseconds(epochMilliseconds: Int64(Date().timeIntervalSince1970) * 1000)
        
        asset = Asset(id: "1234", url: "1234", addedTimestamp: datex, type: type)
    }
    
	var body: some View {
		Text("greet \(asset)")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
