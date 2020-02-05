package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.enums.AlchemistCardTags;

import java.util.ArrayList;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Ignis extends AbstractElement
{// TODO: Add full description to this card
	public static final String ID = AlchemistMod.makeID(Ignis.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	public static final String IMAGE = makeCardPath("Ignis.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;

	private static final int COST = 1;
	private static final int DAMAGE = 8;
	private static final int UPGRADE_PLUS_DMG = 3; //upgrade
	
	public static final int STAGE = 0;
	public static final AbstractElement[] REACTANTS = {}; // we'll actually fill this out once we've added all of the elements

	public Ignis()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = DAMAGE;
		this.exhaust = true;
		
		this.tags.add(AlchemistCardTags.ELEMENT);
		
		// these two lines aren't entirely necessary, but we're gonna do them anyways -Jolkert 2020-02-04
		this.stage = 0;
		this.constituents = null;
		this.reactants = REACTANTS;
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			initializeDescription();
		}
	}
}
