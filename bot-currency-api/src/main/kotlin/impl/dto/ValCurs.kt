package wombatukun.api.currency.impl.dto

import javax.xml.bind.annotation.*

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
class ValCurs() {
	@XmlAttribute(name = "Date") var date: String? = null
	@XmlAttribute(name = "name") var name: String? = null
	@XmlElement(name = "Valute") var valutes: List<Valute> = mutableListOf()

	constructor(date: String?, valutes: List<Valute>): this() {
		this.date = date
		this.valutes = valutes
	}

	override fun toString(): String {
		return "ValCurs(date=$date, name=$name, valutes=$valutes)"
	}
}