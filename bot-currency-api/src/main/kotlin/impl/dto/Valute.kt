package wombatukun.api.currency.impl.dto

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
class Valute() {
	@XmlAttribute(name = "ID") var id: String? = null
	@XmlElement(name = "NumCode") var numCode: String? = null
	@XmlElement(name = "CharCode") var charCode: String? = null
	@XmlElement(name = "Nominal") var nominal: Int? = null
	@XmlElement(name = "Name") var name: String? = null
	@XmlElement(name = "Value") var value: String? = null

	constructor(code: String?, nominal: Int? = 1, value: String?) : this() {
		this.charCode = code
		this.nominal = nominal
		this.value = value
	}

	override fun toString(): String {
		return "Valute(id=$id, numCode=$numCode, charCode=$charCode, nominal=$nominal, name=$name, value=$value)"
	}
}